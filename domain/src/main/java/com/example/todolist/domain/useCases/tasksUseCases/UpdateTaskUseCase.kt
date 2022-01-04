package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.util.Resource

class UpdateTaskUseCase constructor(
    private val componentsRepository: ComponentsRepository
) {

    suspend operator fun invoke(task: Task): Resource<Unit> =
        componentsRepository.updateTask(task)

}