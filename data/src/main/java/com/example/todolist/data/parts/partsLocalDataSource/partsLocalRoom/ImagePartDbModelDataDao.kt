package com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.room.*
import com.example.todolist.data.parts.partsLocalDataSource.ImagePartDbModelLocalDataSource
import com.example.todolist.data.parts.partsLocalDataSource.entities.ImagePartDbModel
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.*
import java.io.IOException

@Dao
interface ImagePartDbModelDataDao : ImagePartDbModelLocalDataSource {

    override fun getImagePartsOfTask(taskId: Long, appContext: Context): Resource<Flow<List<ImagePartDbModel>>> {
        return try {
            Resource.Success(
                getImageMetaInfoOfTask(taskId).map { list ->
                    list.map { imageMetaInfo ->
                        val imageContent = loadPhotoFromInternalStorage(
                            imageMetaInfo.id,
                            appContext
                        )
                        ImagePartDbModel(
                            content = imageContent.content,
                            position = imageMetaInfo.position,
                            parentId = imageMetaInfo.parentId,
                            id = imageMetaInfo.id
                        )
                    }
                }
            )
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun addImagePart(
        imagePart: ImagePartDbModel,
        appContext: Context
    ): Resource<Long> {
        return try {
            val imageMetaInfo = ImageMetaInfo(imagePart.position, imagePart.parentId)
            val id = addImageMetaInfo(imageMetaInfo)
            val imageContent = ImageContent(id, imagePart.content)
            savePhotoToInternalStorage(imageContent, appContext)
            Resource.Success(id)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun deleteImagePart(
        imagePart: ImagePartDbModel,
        appContext: Context
    ): Resource<Unit> {
        return try {
            val imageMetaInfo = ImageMetaInfo(imagePart.position, imagePart.parentId)
            val imageContent = ImageContent(imagePart.id, imagePart.content)
            deleteImageMetaInfo(imageMetaInfo)
            deletePhotoFromInternalStorage(imageContent, appContext)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    @Query("SELECT * FROM imagemetainfo WHERE id = :imageId")
    fun getImageMetaInfoOfTask(imageId: Long): Flow<List<ImageMetaInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImageMetaInfo(imageMetaInfo: ImageMetaInfo): Long

    @Delete
    suspend fun deleteImageMetaInfo(imageMetaInfo: ImageMetaInfo)

    private fun savePhotoToInternalStorage(imageContent: ImageContent, appContext: Context): Boolean =
        try {
            appContext.openFileOutput("${imageContent.id}.jpg", Activity.MODE_PRIVATE).use { stream ->
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

    private fun loadPhotoFromInternalStorage(imageId: Long, appContext: Context): ImageContent {
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

    private fun deletePhotoFromInternalStorage(imageContent: ImageContent, appContext: Context) {
        appContext.filesDir.listFiles()?.filter {
            it.canRead() && it.isFile && it.name.equals("${imageContent.id}.jpg")
        }?.map {
            it.delete()
        } // TODO: exception handling
    }

    data class ImageContent(
        val id: Long,
        val content: ByteArray
    )

    @Entity
    data class ImageMetaInfo(
        val position: Int,
        val parentId: Long,
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0
    )
}