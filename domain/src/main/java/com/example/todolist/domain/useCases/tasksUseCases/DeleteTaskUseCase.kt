package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.repositories.ComponentsRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository
) {

    suspend operator fun invoke(taskId: Long) {
        componentsRepository.deleteTask(taskId)
    }

}
