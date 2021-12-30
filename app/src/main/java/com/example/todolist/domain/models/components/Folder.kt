package com.example.todolist.domain.models.components

import com.example.todolist.di.ApplicationScope
import com.example.todolist.domain.repositories.ComponentsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class Folder @Inject constructor(
    title: String,
    folderId: Long,
    val isPinned: Boolean = false,
    createdDate: Long = System.currentTimeMillis(),
    modifiedDate: Long = System.currentTimeMillis(),
    id: Long = 0,
    private val componentsRepository: ComponentsRepository,
    @ApplicationScope private val applicationScope: CoroutineScope
) : Component(title, folderId, createdDate, modifiedDate, id) {

    private val subComponents: Flow<List<Component>> =
        componentsRepository.getComponentsOfFolder(this)

    override fun delete() {
        applicationScope.launch {
            subComponents.collectLatest {
                for (component in it) {
                    component.delete()
                }
            }
            componentsRepository.deleteFolder(this@Folder)
        }
    }

    fun deleteCompletedTasks() {
        applicationScope.launch {
            subComponents.collectLatest {
                for (component in it) {
                    if (component is Task && component.isCompleted) {
                        component.delete()
                    }
                }
            }
        }
    }

}