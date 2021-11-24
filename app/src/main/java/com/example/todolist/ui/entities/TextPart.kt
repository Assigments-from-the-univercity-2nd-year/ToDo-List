package com.example.todolist.ui.entities

data class TextPart(
    val content: String,
    val position: Int,
    val parentId: Long,
    val id: Long = 0
) : BasePart()
