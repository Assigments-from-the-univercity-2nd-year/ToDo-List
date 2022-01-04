package com.example.todolist.domain.repositories

import com.example.todolist.domain.models.parts.ImagePart
import com.example.todolist.domain.models.parts.TextPart
import com.example.todolist.domain.models.parts.TodoPart
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface PartsRepository {

    fun getTextPartsOfTask(taskId: Long): Resource<Flow<List<TextPart>>>

    fun getImagePartsOfTask(taskId: Long): Resource<Flow<List<ImagePart>>>

    fun getTodoPartsOfTask(taskId: Long): Resource<Flow<List<TodoPart>>>

    suspend fun addTextPart(textPart: TextPart): Resource<Long>

    suspend fun addTodoPart(todoPart: TodoPart): Resource<Long>

    suspend fun addImagePart(imagePart: ImagePart): Resource<Long>

    suspend fun updateTextPart(textPart: TextPart): Resource<Unit>

    suspend fun updateTodoPart(todoPart: TodoPart): Resource<Unit>

    suspend fun deleteTextPart(textPart: TextPart): Resource<Unit>

    suspend fun deleteTodoPart(todoPart: TodoPart): Resource<Unit>

    suspend fun deleteImagePart(imagePart: ImagePart): Resource<Unit>

}