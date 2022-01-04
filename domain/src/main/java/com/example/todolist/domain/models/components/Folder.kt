package com.example.todolist.domain.models.components

import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.useCases.folderUseCases.GetComponentsOfFolderUseCase
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

data class Folder constructor(
    override var title: String,
    override var folderId: Long,
    val isPinned: Boolean,
    override val createdDate: Long,
    override var modifiedDate: Long,
    override val id: Long,
    private val componentsRepository: ComponentsRepository,
    private val getComponentsOfFolderUseCase: GetComponentsOfFolderUseCase,
) : Component(title, folderId, createdDate, modifiedDate, id) {

    private val subComponents: Resource<Flow<List<Component>>>
        by lazy { getComponentsOfFolderUseCase(this) }

    override suspend fun delete(): Resource<Unit> =
        when (val _subComponents = subComponents) { // we need to make _subComponents object because we init subComponents by lazy
            is Resource.Error -> {
                Resource.Error(_subComponents.exception)
                TODO("Logging")
            }
            is Resource.Success -> {
                _subComponents.data.collectLatest {
                    for (component in it) {
                        component.delete()
                    }
                }

                componentsRepository.deleteFolder(this)
                Resource.Success(Unit)
            }
        }

    override suspend fun update(): Resource<Unit> =
        componentsRepository.updateFolder(this)

    suspend fun deleteCompletedTasks(): Resource<Unit> =
        when(val _subComponents = subComponents) { // we need to make _subComponents object because we init subComponents by lazy
            is Resource.Error -> {
                Resource.Error(_subComponents.exception)
                TODO("Logging")
            }
            is Resource.Success -> {
                _subComponents.data.collectLatest {
                    for (component in it) {
                        if (component is Task && component.isCompleted) {
                            component.delete()
                        }
                        if (component is Folder) {
                            component.deleteCompletedTasks()
                        }
                    }
                }

                componentsRepository.deleteFolder(this)
                Resource.Success(Unit)
            }
        }
}
