package com.example.todolist.domain.useCases.todoPartUseCases

import com.example.todolist.domain.models.parts.TextPart
import com.example.todolist.domain.models.parts.TodoPart
import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource
import javax.inject.Inject

class AddTodoPartUseCase @Inject constructor(
    private val partsRepository: PartsRepository
) {

    suspend operator fun invoke(taskId: Long, partPosition: Int): Resource<Long, RepositoryExceptions> {
        require(taskId > 0) {
            "id of task is less or equals than 0: id = $taskId"
        }
        require(partPosition >= 0) {
            "position of part is less than 0: position = $partPosition"
        }

        val todoPart = TodoPart(
            content = "",
            position = partPosition,
            parentId = taskId,
            isCompleted = false,
            id = 0,
        )

        return partsRepository.addTodoPart(todoPart)
    }

}