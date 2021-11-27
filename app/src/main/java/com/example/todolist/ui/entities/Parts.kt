package com.example.todolist.ui.entities

import android.graphics.Bitmap

sealed class BasePart {
    abstract val position: Int
}

data class TextPart(
    val content: String,
    override val position: Int,
    val parentId: Long,
    val id: Long = 0
) : BasePart()

data class TodoPart(
    val content: String,
    override val position: Int,
    val parentId: Long,
    val isCompleted: Boolean = false,
    val id: Long = 0
) : BasePart()

data class ImagePart(
    val content: Bitmap,
    override val position: Int,
    val parentId: Long,
    val id: Long = 0
) : BasePart()