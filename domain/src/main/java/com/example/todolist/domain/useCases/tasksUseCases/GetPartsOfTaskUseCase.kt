package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.models.parts.ImagePart
import com.example.todolist.domain.models.parts.Part
import com.example.todolist.domain.models.parts.TextPart
import com.example.todolist.domain.models.parts.TodoPart
import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.domain.useCases.folderUseCases.GetComponentsOfFolderUseCase
import com.example.todolist.domain.util.Resource
import com.example.todolist.domain.util.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetPartsOfTaskUseCase constructor(
    private val partsRepository: PartsRepository
) {

    operator fun invoke(task: Task): Flow<Resource<List<Part>, GetPartsOfTaskUseCaseException>> {
        return combine(
            partsRepository.getTextPartsOfTask(task.id),
            partsRepository.getImagePartsOfTask(task.id),
            partsRepository.getTodoPartsOfTask(task.id)
        ) { textRes,
            imageRes,
            TodoRes ->

            val textParts = textRes.onFailure {
                return@combine Resource.Failure(GetPartsOfTaskUseCaseException.ProblemWithTextPartsFlow(it.reason))
            }
            val imageParts = imageRes.onFailure {
                return@combine Resource.Failure(GetPartsOfTaskUseCaseException.ProblemWithImagePartsFlow(it.reason))
            }
            val todoParts = TodoRes.onFailure {
                return@combine Resource.Failure(GetPartsOfTaskUseCaseException.ProblemWithTodoPartsFlow(it.reason))
            }

            Resource.Success(
                (textParts as List<Part>).plus(imageParts).plus(todoParts)
                    .sortedBy { it.position }
            )
        }
    }

    sealed class GetPartsOfTaskUseCaseException : Throwable() {
        data class ProblemWithTextPartsFlow(override val cause: Throwable) : GetPartsOfTaskUseCaseException()
        data class ProblemWithImagePartsFlow(override val cause: Throwable) : GetPartsOfTaskUseCaseException()
        data class ProblemWithTodoPartsFlow(override val cause: Throwable) : GetPartsOfTaskUseCaseException()
    }

}