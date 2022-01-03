package com.example.todolist.data.parts.partsLocalDataSource

import android.content.Context
import com.example.todolist.data.parts.partsLocalDataSource.entities.ImagePartDbModel
import kotlinx.coroutines.flow.Flow

interface ImagePartDbModelLocalDataSource {

    fun getImagePartsOfTask(taskId: Long): Flow<List<ImagePartDbModel>>

    suspend fun addImagePart(imagePart: ImagePartDbModel, appContext: Context): Result<Long>

    suspend fun deleteImagePart(imagePart: ImagePartDbModel, appContext: Context): Result<Boolean>

}