package com.example.todolist.data.repository

import com.example.todolist.data.db.PartDatabase
import com.example.todolist.ui.entities.BasePart
import com.example.todolist.ui.entities.TextPart
import com.example.todolist.ui.entities.TodoPart
import com.example.todolist.ui.mappers.TextPartMapper
import com.example.todolist.ui.mappers.TodoPartMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val partDatabase: PartDatabase
) {

    suspend fun insertTextPart(textPart: TextPart): Long =
        partDatabase.textPartDataDao().insertTextPartData(
            TextPartMapper.matToDataModel(textPart)
        )

    fun getPartsOfTaks(taskId: Long): Flow<List<BasePart>> =
        combine(
            getTextPartsOfTask(taskId),
            getTodoPartsOfTask(taskId)
        ) { textParts, todoParts ->
            Pair(textParts, todoParts)
        }.flatMapLatest { (textParts, todoParts) ->
            flowOf(textParts.plus(todoParts).sortedBy { it.position })
        }

    fun getTextPartsOfTask(taskId: Long): Flow<List<TextPart>> =
        partDatabase.textPartDataDao().getTextPartDatasOfTask(taskId)/*.flatMapLatest { list ->
            flow { list.map { TextPartMapper.mapToDomainModel(it) } }
        }*/
            .transformLatest { list ->
                emit(list.map { TextPartMapper.mapToDomainModel(it) })
            }

    fun getTodoPartsOfTask(taskId: Long): Flow<List<TodoPart>> =
        partDatabase.todoPartDataDao().getTodoPartDatasOfTask(taskId).transformLatest { list ->
            emit(list.map { TodoPartMapper.mapToDomainModel(it) })
        }
}