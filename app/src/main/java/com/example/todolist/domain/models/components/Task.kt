package com.example.todolist.domain.models.components

import com.example.todolist.di.ApplicationScope
import com.example.todolist.domain.repositories.ComponentsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Task(
    title: String,
    folderId: Long,
    var isImportant: Boolean = false,
    var isCompleted: Boolean = false,
    createdDate: Long = System.currentTimeMillis(),
    modifiedDate: Long = System.currentTimeMillis(),
    id: Long = 0,
    private val componentsRepository: ComponentsRepository,
    @ApplicationScope private val applicationScope: CoroutineScope
) : Component(title, folderId, createdDate, modifiedDate, id) {

    override fun delete() {
        applicationScope.launch {
            componentsRepository.deleteTask(this@Task)
        }
    }

}