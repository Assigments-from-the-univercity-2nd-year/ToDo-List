package com.example.todolist.domain.repositories

import com.example.todolist.domain.models.parts.ImagePart
import com.example.todolist.domain.models.parts.TextPart
import com.example.todolist.domain.models.parts.TodoPart
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface PartsRepository {

    fun getTextPartsOfTask(taskId: Long): Flow<List<TextPart>>
    fun getImagePartsOfTask(taskId: Long): Flow<List<ImagePart>>
    fun getTodoPartsOfTask(taskId: Long): Flow<List<TodoPart>>

    suspend fun addTextPart(textPart: TextPart): Long
    suspend fun addTodoPart(todoPart: TodoPart): Long
    suspend fun addImagePart(imagePart: ImagePart): Long

    suspend fun updateTextPart(textPart: TextPart)
    suspend fun updateTodoPart(todoPart: TodoPart)
    suspend fun updateImagePart(imagePart: ImagePart)

    suspend fun deleteTextPart(textPart: TextPart)
    suspend fun deleteTodoPart(todoPart: TodoPart)
    suspend fun deleteImagePart(imagePart: ImagePart)

}