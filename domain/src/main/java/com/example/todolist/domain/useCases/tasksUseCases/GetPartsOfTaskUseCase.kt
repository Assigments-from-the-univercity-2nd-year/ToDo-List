package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.models.parts.Part
import com.example.todolist.domain.repositories.PartsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetPartsOfTaskUseCase @Inject constructor(
    private val partsRepository: PartsRepository
) {

    operator fun invoke(taskId: Long): Flow<List<Part>> {
        return combine(
            partsRepository.getTextPartsOfTask(taskId),
            partsRepository.getImagePartsOfTask(taskId),
            partsRepository.getTodoPartsOfTask(taskId)
        ) { textParts, imageParts, todoParts ->
            (textParts as List<Part>).plus(imageParts).plus(todoParts)
                .sortedBy { it.position }
        }
    }

}
