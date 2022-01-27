package com.example.todolist.presentation.entities.parts

import com.example.todolist.domain.models.parts.Part

data class ImagePartUiState(
    override val content: ByteArray,
    override val position: Int,
    override val parentId: Long,
    override val id: Long,
) : PartUiState(content, position, parentId, id) {
    override fun mapToDomain(): Part {
        TODO("Not yet implemented")
    }
}