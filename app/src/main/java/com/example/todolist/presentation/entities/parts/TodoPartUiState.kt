package com.example.todolist.presentation.entities.parts

import com.example.todolist.domain.models.parts.TodoPart

data class TodoPartUiState(
    override val content: String,
    override val position: Int,
    override val parentId: Long,
             val isCompleted: Boolean,
    override val id: Long
) : PartUiState(content, position, parentId, id) {
    override fun mapToDomain(): TodoPart {
        TODO("Not yet implemented")
    }
}