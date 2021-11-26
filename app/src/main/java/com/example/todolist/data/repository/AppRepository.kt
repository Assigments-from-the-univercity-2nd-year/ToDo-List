package com.example.todolist.data.repository

import com.example.todolist.data.db.PartDatabase
import com.example.todolist.ui.entities.BasePart
import com.example.todolist.ui.entities.ImagePart
import com.example.todolist.ui.entities.TextPart
import com.example.todolist.ui.entities.TodoPart
import com.example.todolist.ui.mappers.ImagePartMapper
import com.example.todolist.ui.mappers.TextPartMapper
import com.example.todolist.ui.mappers.TodoPartMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val partDatabase: PartDatabase
) {

    fun getPartsOfTaks(taskId: Long): Flow<List<BasePart>> =
        combine(
            getTextPartsOfTask(taskId),
            getTodoPartsOfTask(taskId),
            getImagePartsOfTask(taskId)
        ) { textParts, todoParts, imageParts ->
            Triple(textParts, todoParts, imageParts)
        }.flatMapLatest { (textParts, todoParts, imageParts) ->
            flowOf(textParts.plus(todoParts).plus(imageParts).sortedBy { it.position })
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

    fun getImagePartsOfTask(taskId: Long): Flow<List<ImagePart>> =
        partDatabase.imagePartDataDao().getImagePartDatasOfTask(taskId).transformLatest { list ->
            emit(list.map { ImagePartMapper.mapToDomainModel(it) })
        }

    suspend fun insertTextPart(textPart: TextPart): Long =
        partDatabase.textPartDataDao().insertTextPartData(
            TextPartMapper.mapToDataModel(textPart)
        )

    suspend fun insertTodoPart(todoPart: TodoPart): Long =
        partDatabase.todoPartDataDao().insertTodoPartData(
            TodoPartMapper.mapToDataModel(todoPart)
        )

    suspend fun insertImagePart(imagePart: ImagePart): Long =
        partDatabase.imagePartDataDao().insertImagePartData(
            ImagePartMapper.mapToDataModel(imagePart)
        )

    suspend fun updateTextPart(textPart: TextPart) =
        partDatabase.textPartDataDao().updateTextPartData(
            TextPartMapper.mapToDataModel(textPart)
        )

    suspend fun updateTodoPart(todoPart: TodoPart) =
        partDatabase.todoPartDataDao().updateTodoPartData(
            TodoPartMapper.mapToDataModel(todoPart)
        )
}