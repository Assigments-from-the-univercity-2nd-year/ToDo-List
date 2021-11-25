package com.example.todolist.ui.entities

data class TodoPart(
    val content: String,
    override val position: Int,
    val parentId: Long,
    val isCompleted: Boolean = false,
    val id: Long = 0
) : BasePart()