package com.example.todolist.domain.useCases.imagePartUseCases

import com.example.todolist.domain.models.parts.ImagePart
import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.util.Resource
import javax.inject.Inject

class DeleteImagePartUseCase @Inject constructor(
    private val partsRepository: PartsRepository
) {

    suspend operator fun invoke(imagePart: ImagePart): Resource<Unit> =
        partsRepository.deleteImagePart(imagePart)

}