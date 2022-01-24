package com.example.todolist.presentation.entities.parts

import com.example.todolist.domain.models.parts.TodoPart

data class TodoPartUiState(
    val content: String,
    val position: Int,
    val parentId: Long,
    val isCompleted: Boolean,
    val id: Long = 0
) : PartUiState() {
    override fun mapToDomain(): TodoPart {
        TODO("Not yet implemented")
    }
}