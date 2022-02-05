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
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PartsRepositoryImpl(
    private val textPartLocalDataSource: TextPartDbModelLocalDataSource,
    private val imagePartLocalDataSource: ImagePartDbModelLocalDataSource,
    private val todoPartLocalDataSource: TodoPartDbModelLocalDataSource,
    @ApplicationContext private val appContext: Context
) : PartsRepository {

    override fun getTextPartsOfTask(taskId: Long): Flow<List<TextPart>> {
        return textPartLocalDataSource.getTextPartsOfTask(taskId)
            .map { it.mapTextPartListToDomain() }
    }

    override fun getImagePartsOfTask(taskId: Long): Flow<List<ImagePart>> {
        return imagePartLocalDataSource.getImagePartsOfTask(taskId, appContext)
            .map { it.mapImagePartListToDomain() }
    }

    override fun getTodoPartsOfTask(taskId: Long): Flow<List<TodoPart>> {
        return todoPartLocalDataSource.getTodoPartOfTask(taskId)
            .map { it.mapTodoPartListToDomain() }
    }

    override suspend fun addTextPart(textPart: TextPart): Long {
        return textPartLocalDataSource.addTextPart(textPart.mapToData())
    }

    override suspend fun addTodoPart(todoPart: TodoPart): Long {
        return todoPartLocalDataSource.addTodoPart(todoPart.mapToData())
    }

    override suspend fun addImagePart(imagePart: ImagePart): Long {
        return imagePartLocalDataSource.addImagePart(imagePart.mapToData(), appContext)
    }

    override suspend fun updateTextPart(textPart: TextPart) {
        textPartLocalDataSource.updateTextPart(textPart.mapToData())
    }

    override suspend fun updateTodoPart(todoPart: TodoPart) {
        todoPartLocalDataSource.updateTodoPart(todoPart.mapToData())
    }

    override suspend fun updateImagePart(imagePart: ImagePart) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTextPart(textPart: TextPart) {
        textPartLocalDataSource.deleteTextPart(textPart.mapToData())
    }

    override suspend fun deleteTodoPart(todoPart: TodoPart) {
        todoPartLocalDataSource.deleteTodoPart(todoPart.mapToData())
    }

    override suspend fun deleteImagePart(imagePart: ImagePart) {
        imagePartLocalDataSource.deleteImagePart(imagePart.mapToData(), appContext)
    }

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

    private fun List<TextPartDbModel>.mapTextPartListToDomain(): List<TextPart> {
        return this.map {
            it.mapToDomain()
        }
    }

    private fun List<ImagePartDbModel>.mapImagePartListToDomain(): List<ImagePart> {
        return this.map {
            it.mapToDomain()
        }
    }

    private fun List<TodoPartDbModel>.mapTodoPartListToDomain(): List<TodoPart> {
        return this.map {
            it.mapToDomain()
        }
    }

    private companion object {
        private const val TAG = "PartsRepositoryImpl"
    }

}
