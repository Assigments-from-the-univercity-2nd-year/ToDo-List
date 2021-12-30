package com.example.todolist.ui.entities

import android.graphics.Bitmap
import com.example.todolist.data.repositories.AppRepository

sealed class BasePart {
    abstract val position: Int

    abstract suspend fun update(position: Int, appRepository: AppRepository)
}

data class TextPart(
    val content: String,
    override val position: Int,
    val parentId: Long,
    val id: Long = 0
) : BasePart() {
    override suspend fun update(position: Int, appRepository: AppRepository) {
        appRepository.updateTextPart(this.copy(position = position))
    }
}

data class TodoPart(
    val content: String,
    override val position: Int,
    val parentId: Long,
    val isCompleted: Boolean = false,
    val id: Long = 0
) : BasePart() {
    override suspend fun update(position: Int, appRepository: AppRepository) {
        appRepository.updateTodoPart(this.copy(position = position))
    }
}

data class ImagePart(
    val content: Bitmap,
    override val position: Int,
    val parentId: Long,
    val id: Long = 0
) : BasePart() {
    override suspend fun update(position: Int, appRepository: AppRepository) {
        appRepository.updateImagePart(this.copy(position = position))
    }
}