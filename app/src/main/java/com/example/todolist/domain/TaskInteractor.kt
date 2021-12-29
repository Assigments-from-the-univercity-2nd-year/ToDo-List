package com.example.todolist.domain

import com.example.todolist.domain.models.parts.Part
import com.example.todolist.domain.models.components.Task
import kotlinx.coroutines.flow.Flow

class TaskInteractor {

    fun addTaskUseCase(task: Task) {
        TODO()
    }

    fun getTaskUseCase(taskId: Long) {
        TODO()
    }

    fun updateTaskUseCase(task: Task) {
        TODO()
    }

    fun deleteTaskUseCase(task: Task) {
        TODO()
    }

    fun getPartsOfTaskUseCase(task: Task): Flow<List<Part>> {
        TODO()
    }

}