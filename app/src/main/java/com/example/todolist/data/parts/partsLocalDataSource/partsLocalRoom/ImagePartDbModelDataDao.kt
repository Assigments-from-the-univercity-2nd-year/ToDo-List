package com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Log
import androidx.room.*
import com.example.todolist.data.parts.partsLocalDataSource.ImagePartDbModelLocalDataSource
import com.example.todolist.data.parts.partsLocalDataSource.entities.ImagePartDbModel
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

@Dao
interface ImagePartDbModelDataDao : ImagePartDbModelLocalDataSource {

    override fun getImagePartsOfTask(taskId: Long): Flow<List<ImagePartDbModel>>

    override suspend fun addImagePart(imagePart: ImagePartDbModel, appContext: Context): Result<Long> {

    }

    override suspend fun deleteImagePart(imagePart: ImagePartDbModel, appContext: Context)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImageMetaInfo(imageMetaInfo: ImageMetaInfo)

    @Delete
    suspend fun deleteImagePart(imageMetaInfo: ImageMetaInfo)

    private fun savePhotoToInternalStorage(imageContent: ImageContent, appContext: Context): Boolean =
        try {
            appContext.openFileOutput("${imageContent.id}.jpg", Activity.MODE_PRIVATE).use { stream ->
                if (!imageContent.content.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace() // TODO: proper handling
            Log.i(this::class.toString(), "savePhotoToInternalStorage: ${e.message}")
            false
        }

    private fun loadPhotoFromInternalStorage(imageId: Long, appContext: Context): ImageContent =
        appContext.filesDir.listFiles()?.filter {
            it.canRead() && it.isFile && it.name.equals("$imageId.jpg")
        }?.map {
            val bytes = it.readBytes()
            ImageContent(
                imageId,
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            )
        } ?: Nothing

    data class ImageContent(
        val id: Long,
        val content: Bitmap
    )

    @Entity
    data class ImageMetaInfo(
        val position: Int,
        val parentId: Long,
        @PrimaryKey(autoGenerate = true)
        val id: Long
    )
}