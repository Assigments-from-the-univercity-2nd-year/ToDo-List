/*
package com.example.todolist.domain.useCases.imagePartUseCases

import com.example.todolist.domain.models.parts.ImagePart
import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource

class DeleteImagePartUseCase constructor(
    private val partsRepository: PartsRepository
) {

    suspend operator fun invoke(imagePart: ImagePart): Resource<Unit, RepositoryExceptions> =
        partsRepository.deleteImagePart(imagePart)

}*/
