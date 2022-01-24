package com.example.todolist.domain.models.parts

import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.domain.util.Resource

data class ImagePart(
    override val content: ByteArray,
    override val position: Int,
    override val parentId: Long,
    override val id: Long
) : Part(content, position, parentId, id) {

    override suspend fun delete(partsRepository: PartsRepository): Resource<Unit, Throwable> {
        return partsRepository.deleteImagePart(this)
    }

    override suspend fun update(partsRepository: PartsRepository): Resource<Unit, Throwable> {
        return partsRepository.updateImagePart(this)
    }

}