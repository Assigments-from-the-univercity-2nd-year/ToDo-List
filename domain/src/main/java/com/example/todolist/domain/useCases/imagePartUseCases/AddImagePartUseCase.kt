package com.example.todolist.domain.useCases.imagePartUseCases

import com.example.todolist.domain.models.parts.ImagePart
import com.example.todolist.domain.repositories.PartsRepository
import javax.inject.Inject

class AddImagePartUseCase @Inject constructor(
    private val partsRepository: PartsRepository
) {

    suspend operator fun invoke(content: ByteArray, taskId: Long, partPosition: Int): Long {
        require(taskId > 0) {
            "id of task is less or equals than 0: id = $taskId"
        }
        require(partPosition >= 0) {
            "position of part is less than 0: position = $partPosition"
        }

        val imagePart = ImagePart(
            content = content,
            position = partPosition,
            parentId = taskId,
            id = 0,
        )

        return partsRepository.addImagePart(imagePart)
    }

}
