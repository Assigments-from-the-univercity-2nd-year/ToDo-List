package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.models.parts.Part
import com.example.todolist.domain.repositories.PartsRepository
import javax.inject.Inject

class DeletePartUseCase @Inject constructor(
    private val partsRepository: PartsRepository
) {

    suspend operator fun invoke(part: Part) {
        part.delete(partsRepository)
    }

}
