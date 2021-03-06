package com.example.todolist.data

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.PartsDatabase
import com.example.todolist.ui.entities.BasePart
import com.example.todolist.ui.entities.ImagePart
import com.example.todolist.ui.entities.TextPart
import com.example.todolist.ui.entities.TodoPart
import com.example.todolist.ui.mappers.ImagePartMapper
import com.example.todolist.ui.mappers.TextPartMapper
import com.example.todolist.ui.mappers.TodoPartMapper
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val partDatabase: PartsDatabase,
    private val appContext: Context
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
        partDatabase.textPartDataDao().getTextPartsOfTask(taskId)
            .transformLatest { list ->
                emit(list.map { TextPartMapper.mapToDomainModel(it) })
            }

    fun getTodoPartsOfTask(taskId: Long): Flow<List<TodoPart>> =
        partDatabase.todoPartDataDao().getTodoPartsOfTask(taskId).transformLatest { list ->
            emit(list.map { TodoPartMapper.mapToDomainModel(it) })
        }

    fun getImagePartsOfTask(taskId: Long): Flow<List<ImagePart>> =
        partDatabase.imagePartDataDao().getImagePartsOfTask(taskId).transformLatest { list ->
            emit(list.map { ImagePartMapper.mapToDomainModel(it, loadPhotoFromInternalStorage(it.id.toString()).first()) })
        }

    suspend fun insertTextPart(textPart: TextPart): Long =
        partDatabase.textPartDataDao().insertTextPartData(
            TextPartMapper.mapToDataModel(textPart)
        )

    suspend fun insertTodoPart(todoPart: TodoPart): Long =
        partDatabase.todoPartDataDao().insertTodoPartData(
            TodoPartMapper.mapToDataModel(todoPart)
        )

    suspend fun insertImagePart(imagePart: ImagePart): Long {
        val newId = partDatabase.imagePartDataDao().insertImagePartData(
            ImagePartMapper.mapToDataModel(imagePart)
        )
        if (savePhotoToInternalStorage(newId.toString(), imagePart.content)) {
            return newId
        }
        return -1 // TODO: throw exception
    }

    suspend fun updateTextPart(textPart: TextPart) =
        partDatabase.textPartDataDao().updateTextPartData(
            TextPartMapper.mapToDataModel(textPart)
        )

    suspend fun updateTodoPart(todoPart: TodoPart) =
        partDatabase.todoPartDataDao().updateTodoPartData(
            TodoPartMapper.mapToDataModel(todoPart)
        )

    suspend fun updateImagePart(imagePart: ImagePart) {
        partDatabase.imagePartDataDao().updateImagePartData(
            ImagePartMapper.mapToDataModel(imagePart)
        )
    }

    suspend fun deleteTextPart(textPart: TextPart) =
        partDatabase.textPartDataDao().deleteTextPartData(
            TextPartMapper.mapToDataModel(textPart)
        )

    suspend fun deleteTodoPart(todoPart: TodoPart) =
        partDatabase.todoPartDataDao().deleteTodoPartData(
            TodoPartMapper.mapToDataModel(todoPart)
        )

    suspend fun deleteImagePart(imagePart: ImagePart) {
        val imagePartData = ImagePartMapper.mapToDataModel(imagePart)
        appContext.filesDir.listFiles()?.filter {
            it.canRead() && it.isFile && it.name.equals("${imagePartData.id}.jpg")
        }?.map {
            it.delete()
        }
        partDatabase.imagePartDataDao().deleteImagePartData(imagePartData) // TODO: delete in safe way
    }

    private fun savePhotoToInternalStorage(filename: String, bitmap: Bitmap): Boolean =
        try {
            appContext.openFileOutput("$filename.jpg", Activity.MODE_PRIVATE).use { stream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace() // TODO: proper handling
            false
        }

    private fun loadPhotoFromInternalStorage(filename: String): List<Bitmap> =
        appContext.filesDir.listFiles()?.filter {
            it.canRead() && it.isFile && it.name.equals("$filename.jpg")
        }?.map {
            val bytes = it.readBytes()
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } ?: listOf()

}