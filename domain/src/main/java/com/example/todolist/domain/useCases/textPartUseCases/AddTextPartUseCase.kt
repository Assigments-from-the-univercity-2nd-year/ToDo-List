package com.example.todolist.domain.useCases.textPartUseCases

import com.example.todolist.domain.models.parts.TextPart
import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource
import javax.inject.Inject

class AddTextPartUseCase @Inject constructor(
    private val partsRepository: PartsRepository
) {

    suspend operator fun invoke(taskId: Long, partPosition: Int): Resource<Long, RepositoryExceptions> {
        require(taskId > 0) {
            "id of task is less or equals than 0: id = $taskId"
        }
        require(partPosition >= 0) {
            "position of part is less than 0: position = $partPosition"
        }

        val textPart = TextPart(
            content = "",
            position = partPosition,
            parentId = taskId,
            id = 0,
        )

        return partsRepository.addTextPart(textPart)
    }

}