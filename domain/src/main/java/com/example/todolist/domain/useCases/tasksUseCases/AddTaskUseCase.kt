package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository
) {

    suspend operator fun invoke(parentFolderId: Long): Resource<Long, RepositoryExceptions> {
        require(parentFolderId > 0) {
            "id of parent folder is less than 0: id = $parentFolderId"
        }

        val task = Task(
            title = "New task",
            folderId = parentFolderId,
            isImportant = false,
            isCompleted = false,
            createdDate = System.currentTimeMillis(),
            modifiedDate = System.currentTimeMillis(),
            id = 0,
        )

        return componentsRepository.addTask(task)
    }

    suspend operator fun invoke(task: Task): Resource<Long, RepositoryExceptions> {
        TODO()
    }

}