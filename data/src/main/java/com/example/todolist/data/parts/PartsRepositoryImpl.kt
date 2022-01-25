package com.example.todolist.data.parts

import android.content.Context
import android.util.Log
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
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class PartsRepositoryImpl(
    private val textPartLocalDataSource: TextPartDbModelLocalDataSource,
    private val imagePartLocalDataSource: ImagePartDbModelLocalDataSource,
    private val todoPartLocalDataSource: TodoPartDbModelLocalDataSource,
    @ApplicationContext private val appContext: Context
) : PartsRepository {

    override fun getTextPartsOfTask(taskId: Long): Flow<Resource<List<TextPart>, RepositoryExceptions>> {
        return textPartLocalDataSource.getTextPartsOfTask(taskId)
            .map { Resource.Success(it.mapTextPartListToDomain()) as Resource<List<TextPart>, RepositoryExceptions> }
            .catch { exception ->
                Log.e(TAG, "getTextPartsOfTask: ", exception)
                emit(Resource.Failure(reason = RepositoryExceptions.UnknownException(exception.cause)))
            }
    }

    override fun getImagePartsOfTask(taskId: Long): Flow<Resource<List<ImagePart>, RepositoryExceptions>> {
        return imagePartLocalDataSource.getImagePartsOfTask(taskId, appContext)
            .map { Resource.Success(it.mapImagePartListToDomain()) as Resource<List<ImagePart>, RepositoryExceptions> }
            .catch { exception ->
                Log.e(TAG, "getImagePartsOfTask: ", exception)
                emit(Resource.Failure(reason = RepositoryExceptions.UnknownException(exception.cause)))
            }
    }

    override fun getTodoPartsOfTask(taskId: Long): Flow<Resource<List<TodoPart>, RepositoryExceptions>> {
        return todoPartLocalDataSource.getTodoPartOfTask(taskId)
            .map { Resource.Success(it.mapTodoPartListToDomain()) as Resource<List<TodoPart>, RepositoryExceptions> }
            .catch { exception ->
                Log.e(TAG, "getTodoPartsOfTask: ", exception)
                emit(Resource.Failure(reason = RepositoryExceptions.UnknownException(exception.cause)))
            }
    }

    override suspend fun addTextPart(textPart: TextPart): Resource<Long, RepositoryExceptions> {
        return try {
            Resource.Success(textPartLocalDataSource.addTextPart(textPart.mapToData()))
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun addTodoPart(todoPart: TodoPart): Resource<Long, RepositoryExceptions> {
        return try {
            Resource.Success(todoPartLocalDataSource.addTodoPart(todoPart.mapToData()))
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun addImagePart(imagePart: ImagePart): Resource<Long, RepositoryExceptions> {
        return try {
            Resource.Success(imagePartLocalDataSource.addImagePart(imagePart.mapToData(), appContext))
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun updateTextPart(textPart: TextPart): Resource<Unit, RepositoryExceptions> {
        return try {
            Resource.Success(textPartLocalDataSource.updateTextPart(textPart.mapToData()))
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun updateTodoPart(todoPart: TodoPart): Resource<Unit, RepositoryExceptions> {
        return try {
            Resource.Success(todoPartLocalDataSource.updateTodoPart(todoPart.mapToData()))
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun updateImagePart(imagePart: ImagePart): Resource<Unit, RepositoryExceptions> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTextPart(textPart: TextPart): Resource<Unit, RepositoryExceptions> {
        return try {
            Resource.Success(textPartLocalDataSource.deleteTextPart(textPart.mapToData()))
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun deleteTodoPart(todoPart: TodoPart): Resource<Unit, RepositoryExceptions> {
        return try {
            Resource.Success(todoPartLocalDataSource.deleteTodoPart(todoPart.mapToData()))
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun deleteImagePart(imagePart: ImagePart): Resource<Unit, RepositoryExceptions> {
        return try {
            Resource.Success(imagePartLocalDataSource.deleteImagePart(imagePart.mapToData(), appContext))
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
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
