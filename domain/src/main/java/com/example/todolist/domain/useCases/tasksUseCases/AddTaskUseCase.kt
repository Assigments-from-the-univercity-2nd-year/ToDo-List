package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.util.Resource

class AddTaskUseCase constructor(
    private val componentsRepository: ComponentsRepository
) {

    suspend operator fun invoke(task: Task): Resource<Long> =
        componentsRepository.addTask(task)

}