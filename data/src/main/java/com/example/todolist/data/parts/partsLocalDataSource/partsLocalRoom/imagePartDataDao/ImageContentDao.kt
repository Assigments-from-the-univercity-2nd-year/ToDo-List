package com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.imagePartDataDao

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.imagePartDataDao.entities.ImageContent
import java.io.IOException


interface ImageContentDao {

    fun savePhotoToInternalStorage(imageContent: ImageContent, appContext: Context): Boolean {
        return try {
            appContext.openFileOutput("${imageContent.id}.jpg", Activity.MODE_PRIVATE)
                .use { stream ->
                    val bitmap = BitmapFactory.decodeByteArray(
                        imageContent.content,
                        0,
                        imageContent.content.size
                    )
                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
                        throw IOException("Couldn't save bitmap.")
                    }
                }
            true
        } catch (e: IOException) {
            e.printStackTrace() // TODO: proper handling
            Log.i(this::class.toString(), "savePhotoToInternalStorage: ${e.message}")
            false
        }
    }

    fun loadPhotoFromInternalStorage(imageId: Long, appContext: Context): ImageContent {
        val list = appContext.filesDir.listFiles()?.filter {
            it.canRead() && it.isFile && it.name.equals("$imageId.jpg")
        }?.map {
            ImageContent(imageId, it.readBytes())
        } ?: emptyList()

        return when(list.size) {
            0 -> throw Exception("no such element in internal store")
            1 -> list[0]
            else -> throw Exception("many elements were found in internal store")
        }
    }

    fun deletePhotoFromInternalStorage(imageContent: ImageContent, appContext: Context) {
        appContext.filesDir.listFiles()?.filter {
            it.canRead() && it.isFile && it.name.equals("${imageContent.id}.jpg")
        }?.map {
            it.delete()
        }
    }

}