package com.example.todolist.domain.models.parts

import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource

class TextPart(
    override val content: String,
    override val position: Int,
    override val parentId: Long,
    override val id: Long
) : Part(content, position, parentId, id) {

    override suspend fun delete(partsRepository: PartsRepository): Resource<Unit, RepositoryExceptions> {
        return partsRepository.deleteTextPart(this)
    }

    override suspend fun update(partsRepository: PartsRepository): Resource<Unit, RepositoryExceptions> {
        return partsRepository.updateTextPart(this)
    }

}