package com.example.todolist.domain.models.components

import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.useCases.folderUseCases.GetComponentsOfFolderUseCase
import com.example.todolist.domain.util.Resource
import com.example.todolist.domain.util.onFailure
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class Folder @Inject constructor(
    override var title: String,
    override var folderId: Long,
    val isPinned: Boolean,
    override val createdDate: Long,
    override var modifiedDate: Long,
    override val id: Long,
    private val getComponentsOfFolderUseCase: GetComponentsOfFolderUseCase,
) : Component(title, folderId, createdDate, modifiedDate, id) {

    private val subComponents: Flow<Resource<List<Component>, GetComponentsOfFolderUseCase.GetComponentsOfFolderUseCaseException>>
            by lazy { getComponentsOfFolderUseCase(this) }

    override suspend fun delete(
        componentsRepository: ComponentsRepository
    ): Resource<Unit, FolderExceptions> {
        val listOfComponents = subComponents.first()
            .onFailure { return Resource.Failure(FolderExceptions.CantGetSubComponentsException(it.reason)) }

        listOfComponents.forEach { component ->
            component.delete(componentsRepository).onFailure {
                return Resource.Failure(FolderExceptions.CantDeleteSubComponentException(it.reason))
            }
        }

        return Resource.Success(Unit)
    }

    override suspend fun update(
        componentsRepository: ComponentsRepository
    ): Resource<Unit, RepositoryExceptions> =
        componentsRepository.updateFolder(this)

    suspend fun deleteCompletedTasks(
        componentsRepository: ComponentsRepository
    ): Resource<Unit, FolderExceptions> {
        val listOfComponents = subComponents.first()
            .onFailure { return Resource.Failure(FolderExceptions.CantGetSubComponentsException(it.reason)) }

        listOfComponents.forEach { component ->
            if (component is Task && component.isCompleted) {
                component.delete(componentsRepository).onFailure {
                    return Resource.Failure(FolderExceptions.CantDeleteSubComponentException(it.reason))
                }
            }
            if (component is Folder) {
                component.deleteCompletedTasks(componentsRepository).onFailure {
                    return Resource.Failure(FolderExceptions.CantDeleteSubComponentException(it.reason))
                }
            }
        }

        return Resource.Success(Unit)
    }

    sealed class FolderExceptions : Throwable() {
        data class CantGetSubComponentsException(override val cause: Throwable) : FolderExceptions()
        data class CantDeleteSubComponentException(override val cause: Throwable) :
            FolderExceptions()
    }
}
