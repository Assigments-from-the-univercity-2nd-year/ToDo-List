package com.example.todolist.domain.models.components

import com.example.todolist.domain.repositories.ComponentsRepository
import javax.inject.Inject

data class Folder @Inject constructor(
    override val title: String = "New folder",
    override val parentFolderId: Long,
             val isPinned: Boolean = false,
    override val createdDate: Long = System.currentTimeMillis(),
    override val modifiedDate: Long = System.currentTimeMillis(),
    override val id: Long = 0,
             val subComponents: List<Component> = emptyList(),
) : Component(title, parentFolderId, createdDate, modifiedDate, id) {

    override suspend fun delete(componentsRepository: ComponentsRepository) {
        subComponents.forEach { it.delete(componentsRepository) }
        updateModificationDateOfParentFolder(componentsRepository)
    }

    suspend fun deleteCompletedTasks(componentsRepository: ComponentsRepository) {
        subComponents.forEach { component ->
            if (component is Task && component.isCompleted) { component.delete(componentsRepository) }
            if (component is Folder) { component.deleteCompletedTasks(componentsRepository) }
        }
        updateModificationDateOfParentFolder(componentsRepository)
    }

    override suspend fun update(componentsRepository: ComponentsRepository) {
        componentsRepository.updateFolder(this)
        updateModificationDateOfParentFolder(componentsRepository)
    }

}
