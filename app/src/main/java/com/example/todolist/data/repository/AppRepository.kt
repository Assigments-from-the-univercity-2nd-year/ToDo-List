package com.example.todolist.data.repository

import com.example.todolist.data.db.PartDatabase
import com.example.todolist.ui.entities.TextPart
import com.example.todolist.ui.mappers.TextPartMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transformLatest
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val partDatabase: PartDatabase
) {

    suspend fun insertTextPart(textPart: TextPart): Long =
        partDatabase.textPartDataDao().insertTextPartData(
            TextPartMapper.matToDataModel(textPart)
        )

    fun getTextPartsOfTask(taskId: Long): Flow<List<TextPart>> =
        partDatabase.textPartDataDao().getTextPartDatasOfTask(taskId)/*.flatMapLatest { list ->
            flow { list.map { TextPartMapper.mapToDomainModel(it) } }
        }*/
            .transformLatest { list ->
                emit(list.map { TextPartMapper.mapToDomainModel(it) })
            }
}