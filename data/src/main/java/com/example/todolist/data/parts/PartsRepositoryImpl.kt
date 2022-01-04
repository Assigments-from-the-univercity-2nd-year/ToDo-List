package com.example.todolist.data.parts

import android.content.Context
import com.example.todolist.data.parts.partsLocalDataSource.ImagePartDbModelLocalDataSource
import com.example.todolist.data.parts.partsLocalDataSource.TextPartDbModelLocalDataSource
import com.example.todolist.data.parts.partsLocalDataSource.TodoPartDbModelLocalDataSource
import com.example.todolist.data.parts.partsLocalDataSource.entities.ImagePartDbModel
import com.example.todolist.data.parts.partsLocalDataSource.entities.TextPartDbModel
import com.example.todolist.data.parts.partsLocalDataSource.entities.TodoPartDbModel
import com.example.todolist.domain.models.parts.ImagePart
import com.example.todolist.domain.models.parts.TextPart
import com.example.todolist.domain.models.parts.TodoPart
import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PartsRepositoryImpl(
    private val textPartLocalDataSource: TextPartDbModelLocalDataSource,
    private val imagePartLocalDataSource: ImagePartDbModelLocalDataSource,
    private val todoPartLocalDataSource: TodoPartDbModelLocalDataSource,
    private val appContext: Context
) : PartsRepository {

    override fun getTextPartsOfTask(taskId: Long): Resource<Flow<List<TextPart>>> =
        when(val flow = textPartLocalDataSource.getTextPartsOfTask(taskId)) {
            is Resource.Error -> Resource.Error(flow.exception)
            is Resource.Success -> Resource.Success(flow.data.mapTextPartFlowToDomain())
        }

    override fun getImagePartsOfTask(taskId: Long): Resource<Flow<List<ImagePart>>> =
        when(val flow = imagePartLocalDataSource.getImagePartsOfTask(taskId, appContext)) {
            is Resource.Error -> Resource.Error(flow.exception)
            is Resource.Success -> Resource.Success(flow.data.mapImagePartFlowToDomain())
        }

    override fun getTodoPartsOfTask(taskId: Long): Resource<Flow<List<TodoPart>>> =
        when(val flow = todoPartLocalDataSource.getTodoPartOfTask(taskId)) {
            is Resource.Error -> Resource.Error(flow.exception)
            is Resource.Success -> Resource.Success(flow.data.mapTodoPartFlowToDomain())
        }

    override suspend fun addTextPart(textPart: TextPart): Resource<Long> =
        textPartLocalDataSource.addTextPart(textPart.mapToData())

    override suspend fun addTodoPart(todoPart: TodoPart): Resource<Long> =
        todoPartLocalDataSource.addTodoPart(todoPart.mapToData())

    override suspend fun addImagePart(imagePart: ImagePart): Resource<Long> =
        imagePartLocalDataSource.addImagePart(imagePart.mapToData(), appContext)

    override suspend fun updateTextPart(textPart: TextPart): Resource<Unit> =
        textPartLocalDataSource.updateTextPart(textPart.mapToData())

    override suspend fun updateTodoPart(todoPart: TodoPart): Resource<Unit> =
        todoPartLocalDataSource.updateTodoPart(todoPart.mapToData())

    override suspend fun deleteTextPart(textPart: TextPart): Resource<Unit> =
        textPartLocalDataSource.deleteTextPart(textPart.mapToData())

    override suspend fun deleteTodoPart(todoPart: TodoPart): Resource<Unit> =
        todoPartLocalDataSource.deleteTodoPart(todoPart.mapToData())

    override suspend fun deleteImagePart(imagePart: ImagePart): Resource<Unit> =
        imagePartLocalDataSource.deleteImagePart(imagePart.mapToData(), appContext)

    private fun TextPartDbModel.mapToDomain(): TextPart {
        TODO("Not yet implemented")
    }

    private fun TextPart.mapToData(): TextPartDbModel {
        TODO("Not yet implemented")
    }

    private fun ImagePartDbModel.mapToDomain(): ImagePart {
        TODO("Not yet implemented")
    }

    private fun ImagePart.mapToData(): ImagePartDbModel {
        TODO("Not yet implemented")
    }

    private fun TodoPartDbModel.mapToDomain(): TodoPart {
        TODO("Not yet implemented")
    }

    private fun TodoPart.mapToData(): TodoPartDbModel {
        TODO("Not yet implemented")
    }

    private fun Flow<List<TextPartDbModel>>.mapTextPartFlowToDomain(): Flow<List<TextPart>> =
        this.map { list ->
            list.map {
                it.mapToDomain()
            }
        }

    private fun Flow<List<ImagePartDbModel>>.mapImagePartFlowToDomain(): Flow<List<ImagePart>> =
        this.map { list ->
            list.map {
                it.mapToDomain()
            }
        }

    private fun Flow<List<TodoPartDbModel>>.mapTodoPartFlowToDomain(): Flow<List<TodoPart>> =
        this.map { list ->
            list.map {
                it.mapToDomain()
            }
        }

}