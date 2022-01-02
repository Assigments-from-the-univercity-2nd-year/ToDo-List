package com.example.todolist.domain.repositories

import com.example.todolist.domain.models.parts.Part
import com.example.todolist.ui.entities.ImagePart
import com.example.todolist.ui.entities.TextPart
import com.example.todolist.ui.entities.TodoPart
import kotlinx.coroutines.flow.Flow

interface PartsRepository {

    fun getPartsOfTask(taskId: Long): Flow<List<Part>>

    suspend fun addTextPart(textPart: TextPart): Long

    suspend fun addTodoPart(todoPart: TodoPart): Long

    suspend fun addImagePart(imagePart: ImagePart): Long

    suspend fun deleteTextPart(textPart: TextPart)

    suspend fun deleteTodoPart(todoPart: TodoPart)

    suspend fun deleteImagePart(imagePart: ImagePart)

}