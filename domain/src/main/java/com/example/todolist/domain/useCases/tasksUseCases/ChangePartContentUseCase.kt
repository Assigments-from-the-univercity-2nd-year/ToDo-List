package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.models.parts.Part
import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource
import javax.inject.Inject

class ChangePartContentUseCase @Inject constructor(
    private val partsRepository: PartsRepository
) {

    suspend operator fun invoke(part: Part, newContent: String): Resource<Unit, Throwable> {

        return part.update(partsRepository)
    }

}