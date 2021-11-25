package com.example.todolist.ui.entities

data class TodoPart(
    val content: List<String>,
    override val position: Int,
    val parentId: Long,
    val id: Long = 0
) : BasePart()