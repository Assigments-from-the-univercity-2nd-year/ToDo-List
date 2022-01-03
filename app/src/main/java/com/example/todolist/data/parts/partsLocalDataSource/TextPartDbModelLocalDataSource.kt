package com.example.todolist.data.parts.partsLocalDataSource

import com.example.todolist.data.parts.partsLocalDataSource.entities.TextPartDbModel
import kotlinx.coroutines.flow.Flow

interface TextPartDbModelLocalDataSource {

    fun getTextPartsOfTask(taskId: Long): Flow<List<TextPartDbModel>>

    suspend fun addTextPart(textPart: TextPartDbModel): Long

    suspend fun deleteTextPart(textPart: TextPartDbModel)

}