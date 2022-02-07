package com.example.todolist.domain.models.components

import com.example.todolist.domain.repositories.ComponentsRepository

data class Task constructor(
    override val title: String = "New task",
    override val parentFolderId: Long,
             val isImportant: Boolean = false,
             val isCompleted: Boolean = false,
    override val createdDate: Long = System.currentTimeMillis(),
    override val modifiedDate: Long = System.currentTimeMillis(),
    override val id: Long = 0,
) : Component(title, parentFolderId, createdDate, modifiedDate, id) {

    override suspend fun delete(componentsRepository: ComponentsRepository) {
        componentsRepository.deleteTask(this.id)
        updateModificationDateOfParentFolder(componentsRepository)
    }

    override suspend fun update(componentsRepository: ComponentsRepository) {
        componentsRepository.updateTask(this)
        updateModificationDateOfParentFolder(componentsRepository)
    }

}
