package com.example.todolist.data.parts.partsLocalDataSource

import android.content.Context
import com.example.todolist.data.parts.partsLocalDataSource.entities.ImagePartDbModel
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ImagePartDbModelLocalDataSource {

    fun getImagePartsOfTask(taskId: Long, appContext: Context): Flow<List<ImagePartDbModel>>

    suspend fun addImagePart(imagePart: ImagePartDbModel, appContext: Context): Long

    suspend fun deleteImagePart(imagePart: ImagePartDbModel, appContext: Context): Unit

}