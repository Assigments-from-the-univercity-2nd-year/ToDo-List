package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository
) {

    suspend operator fun invoke(task: Task): Resource<Unit, RepositoryExceptions> =
        componentsRepository.deleteTask(task)

}