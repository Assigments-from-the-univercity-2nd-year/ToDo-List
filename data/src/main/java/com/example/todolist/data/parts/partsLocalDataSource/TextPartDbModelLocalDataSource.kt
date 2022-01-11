package com.example.todolist.data.parts.partsLocalDataSource

import com.example.todolist.data.parts.partsLocalDataSource.entities.TextPartDbModel
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface TextPartDbModelLocalDataSource {

    fun getTextPartsOfTask(taskId: Long): Flow<List<TextPartDbModel>>

    suspend fun addTextPart(textPart: TextPartDbModel): Long

    suspend fun updateTextPart(textPart: TextPartDbModel): Unit

    suspend fun deleteTextPart(textPart: TextPartDbModel): Unit

}