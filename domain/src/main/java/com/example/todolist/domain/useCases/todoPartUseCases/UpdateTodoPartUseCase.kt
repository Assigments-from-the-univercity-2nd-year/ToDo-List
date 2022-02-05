package com.example.todolist.domain.useCases.todoPartUseCases

import com.example.todolist.domain.models.parts.TodoPart
import com.example.todolist.domain.repositories.PartsRepository
import javax.inject.Inject

class UpdateTodoPartUseCase @Inject constructor(
    private val partsRepository: PartsRepository
) {

    suspend operator fun invoke(todoPart: TodoPart) {
        partsRepository.updateTodoPart(todoPart)
    }

}
