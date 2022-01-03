package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.models.parts.ImagePart
import com.example.todolist.domain.models.parts.Part
import com.example.todolist.domain.models.parts.TextPart
import com.example.todolist.domain.models.parts.TodoPart
import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetPartsOfTaskUseCase @Inject constructor(
    private val partsRepository: PartsRepository
) {

    operator fun invoke(task: Task): Resource<Flow<List<Part>>> {

        val textPartResourceFlow = partsRepository.getTextPartsOfTask(task.id)
        val imagePartResourceFlow = partsRepository.getImagePartsOfTask(task.id)
        val todoPartResourceFlow = partsRepository.getTodoPartsOfTask(task.id)

        return when {
            textPartResourceFlow is Resource.Error -> Resource.Error(textPartResourceFlow.exception)
            imagePartResourceFlow is Resource.Error -> Resource.Error(imagePartResourceFlow.exception)
            todoPartResourceFlow is Resource.Error -> Resource.Error(todoPartResourceFlow.exception)

            else -> onSuccess(
                textPartResourceFlow as Resource.Success<Flow<List<TextPart>>>,
                imagePartResourceFlow as Resource.Success<Flow<List<ImagePart>>>,
                todoPartResourceFlow as Resource.Success<Flow<List<TodoPart>>>
            )
        }
    }

    private fun onSuccess(
        textPartResourceFlow: Resource.Success<Flow<List<TextPart>>>,
        imagePartResourceFlow: Resource.Success<Flow<List<ImagePart>>>,
        todoPartResourceFlow: Resource.Success<Flow<List<TodoPart>>>
    ) : Resource<Flow<List<Part>>> =
        Resource.Success(
            combine(
                textPartResourceFlow.data,
                imagePartResourceFlow.data,
                todoPartResourceFlow.data
            ) {
                textParts, imageParts, todoParts ->
                (textParts as List<Part>).plus(imageParts).plus(todoParts)
                    .sortedBy { it.position }
            }
        )

}