package com.example.todolist.presentation.entities.parts

import com.example.todolist.domain.models.parts.Part

data class TextPartUiState(
    override val content: String,
    override val position: Int,
    override val parentId: Long,
    override val id: Long
) : PartUiState(content, position, parentId, id) {
    override fun mapToDomain(): Part {
        TODO("Not yet implemented")
    }
}