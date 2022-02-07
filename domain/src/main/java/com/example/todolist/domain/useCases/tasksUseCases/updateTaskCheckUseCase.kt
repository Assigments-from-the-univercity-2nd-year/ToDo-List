package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.repositories.ComponentsRepository
import javax.inject.Inject

class updateTaskCheckUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository
) {

    suspend operator fun invoke(taskId: Long, isCompleted: Boolean) {
        componentsRepository.updateTask(
            componentsRepository.getTask(taskId).copy(isCompleted = isCompleted)
        )
    }

}