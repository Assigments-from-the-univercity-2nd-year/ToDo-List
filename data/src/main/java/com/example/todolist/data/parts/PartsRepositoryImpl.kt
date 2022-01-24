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
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PartsRepositoryImpl(
    private val textPartLocalDataSource: TextPartDbModelLocalDataSource,
    private val imagePartLocalDataSource: ImagePartDbModelLocalDataSource,
    private val todoPartLocalDataSource: TodoPartDbModelLocalDataSource,
    private val appContext: Context
) : PartsRepository {

    override fun getTextPartsOfTask(taskId: Long): Flow<Resource<List<TextPart>, RepositoryExceptions>> {
        return textPartLocalDataSource.getTextPartsOfTask(taskId).map {
            Resource.Success(it.mapTextPartListToDomain())
        }
    }

    override fun getImagePartsOfTask(taskId: Long): Flow<Resource<List<ImagePart>, RepositoryExceptions>> {
        return imagePartLocalDataSource.getImagePartsOfTask(taskId, appContext).map {
            Resource.Success(it.mapImagePartListToDomain())
        }
    }

    override fun getTodoPartsOfTask(taskId: Long): Flow<Resource<List<TodoPart>, RepositoryExceptions>> {
        return todoPartLocalDataSource.getTodoPartOfTask(taskId).map {
            Resource.Success(it.mapTodoPartListToDomain())
        }
    }

    override suspend fun addTextPart(textPart: TextPart): Resource<Long, RepositoryExceptions> =
        Resource.Success(textPartLocalDataSource.addTextPart(textPart.mapToData()))

    override suspend fun addTodoPart(todoPart: TodoPart): Resource<Long, RepositoryExceptions> =
        Resource.Success(todoPartLocalDataSource.addTodoPart(todoPart.mapToData()))

    override suspend fun addImagePart(imagePart: ImagePart): Resource<Long, RepositoryExceptions> =
        Resource.Success(imagePartLocalDataSource.addImagePart(imagePart.mapToData(), appContext))

    override suspend fun updateTextPart(textPart: TextPart): Resource<Unit, RepositoryExceptions> =
        Resource.Success(textPartLocalDataSource.updateTextPart(textPart.mapToData()))

    override suspend fun updateTodoPart(todoPart: TodoPart): Resource<Unit, RepositoryExceptions> =
        Resource.Success(todoPartLocalDataSource.updateTodoPart(todoPart.mapToData()))

    override suspend fun updateImagePart(imagePart: ImagePart): Resource<Unit, RepositoryExceptions> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTextPart(textPart: TextPart): Resource<Unit, RepositoryExceptions> =
        Resource.Success(textPartLocalDataSource.deleteTextPart(textPart.mapToData()))

    override suspend fun deleteTodoPart(todoPart: TodoPart): Resource<Unit, RepositoryExceptions> =
        Resource.Success(todoPartLocalDataSource.deleteTodoPart(todoPart.mapToData()))

    override suspend fun deleteImagePart(imagePart: ImagePart): Resource<Unit, RepositoryExceptions> =
        Resource.Success(imagePartLocalDataSource.deleteImagePart(imagePart.mapToData(), appContext))

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

    private fun List<TextPartDbModel>.mapTextPartListToDomain(): List<TextPart> =
        this.map {
            it.mapToDomain()
        }

    private fun List<ImagePartDbModel>.mapImagePartListToDomain(): List<ImagePart> =
        this.map {
            it.mapToDomain()
        }

    private fun List<TodoPartDbModel>.mapTodoPartListToDomain(): List<TodoPart> =
        this.map {
            it.mapToDomain()
        }

}
