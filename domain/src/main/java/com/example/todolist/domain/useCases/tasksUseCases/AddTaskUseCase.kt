package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.repositories.ComponentsRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository
) {

    suspend operator fun invoke(parentFolderId: Long): Long {
        require(parentFolderId > 0) { "id of parent folder is less than 0: id = $parentFolderId" }
        val task = Task(parentFolderId = parentFolderId)
        return componentsRepository.addTask(task)
    }

    suspend operator fun invoke(task: Task): Long {
        return componentsRepository.addTask(task)
    }

}