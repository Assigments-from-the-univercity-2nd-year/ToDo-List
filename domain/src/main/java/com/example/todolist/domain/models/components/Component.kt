package com.example.todolist.domain.models.components

import com.example.todolist.domain.repositories.ComponentsRepository

abstract class Component(
    open val title: String,
    open val parentFolderId: Long,
    open val createdDate: Long,
    open val modifiedDate: Long,
    open val id: Long
) {

    abstract suspend fun delete(componentsRepository: ComponentsRepository)

    abstract suspend fun update(componentsRepository: ComponentsRepository)

    suspend fun getParentFolder(componentsRepository: ComponentsRepository): Folder {
        return componentsRepository.getFolder(this.parentFolderId)
    }

    protected suspend fun updateModificationDateOfParentFolder(componentsRepository: ComponentsRepository) {
        getParentFolder(componentsRepository)
            .copy(modifiedDate = System.currentTimeMillis())
            .update(componentsRepository)
    }

}
