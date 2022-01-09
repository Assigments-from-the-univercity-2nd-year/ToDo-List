package com.example.todolist.domain.useCases.textPartUseCases

import com.example.todolist.domain.models.parts.TextPart
import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource

class AddTextPartUseCase constructor(
    private val partsRepository: PartsRepository
) {

    suspend operator fun invoke(textPart: TextPart): Resource<Long, RepositoryExceptions> =
        partsRepository.addTextPart(textPart)

}