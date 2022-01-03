package com.example.todolist.domain.models.components

import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.util.Resource

data class Task(
    override var title: String,
    override var folderId: Long,
    var isImportant: Boolean,
    var isCompleted: Boolean,
    override val createdDate: Long,
    override var modifiedDate: Long,
    override val id: Long,
    private val componentsRepository: ComponentsRepository,
) : Component(title, folderId, createdDate, modifiedDate, id) {

    override suspend fun delete(): Resource<Unit> =
        componentsRepository.deleteTask(this)

    override suspend fun update(): Resource<Unit> =
        componentsRepository.updateTask(this)

}