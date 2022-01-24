package com.example.todolist.domain.repositories

import com.example.todolist.domain.models.parts.ImagePart
import com.example.todolist.domain.models.parts.TextPart
import com.example.todolist.domain.models.parts.TodoPart
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface PartsRepository {

    fun getTextPartsOfTask(taskId: Long): Flow<Resource<List<TextPart>, RepositoryExceptions>>

    fun getImagePartsOfTask(taskId: Long): Flow<Resource<List<ImagePart>, RepositoryExceptions>>

    fun getTodoPartsOfTask(taskId: Long): Flow<Resource<List<TodoPart>, RepositoryExceptions>>

    suspend fun addTextPart(textPart: TextPart): Resource<Long, RepositoryExceptions>

    suspend fun addTodoPart(todoPart: TodoPart): Resource<Long, RepositoryExceptions>

    suspend fun addImagePart(imagePart: ImagePart): Resource<Long, RepositoryExceptions>

    suspend fun updateTextPart(textPart: TextPart): Resource<Unit, RepositoryExceptions>

    suspend fun updateTodoPart(todoPart: TodoPart): Resource<Unit, RepositoryExceptions>

    suspend fun updateImagePart(imagePart: ImagePart): Resource<Unit, RepositoryExceptions>

    suspend fun deleteTextPart(textPart: TextPart): Resource<Unit, RepositoryExceptions>

    suspend fun deleteTodoPart(todoPart: TodoPart): Resource<Unit, RepositoryExceptions>

    suspend fun deleteImagePart(imagePart: ImagePart): Resource<Unit, RepositoryExceptions>

}