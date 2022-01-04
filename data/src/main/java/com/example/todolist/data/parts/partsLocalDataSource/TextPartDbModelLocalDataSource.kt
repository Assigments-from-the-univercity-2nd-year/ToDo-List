package com.example.todolist.data.parts.partsLocalDataSource

import com.example.todolist.data.parts.partsLocalDataSource.entities.TextPartDbModel
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface TextPartDbModelLocalDataSource {

    fun getTextPartsOfTask(taskId: Long): Resource<Flow<List<TextPartDbModel>>>

    suspend fun addTextPart(textPart: TextPartDbModel): Resource<Long>

    suspend fun updateTextPart(textPart: TextPartDbModel): Resource<Unit>

    suspend fun deleteTextPart(textPart: TextPartDbModel): Resource<Unit>

}