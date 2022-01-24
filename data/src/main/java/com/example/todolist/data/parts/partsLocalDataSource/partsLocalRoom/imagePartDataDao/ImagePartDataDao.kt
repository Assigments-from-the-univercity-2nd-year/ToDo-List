package com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.imagePartDataDao

import android.content.Context
import com.example.todolist.data.parts.partsLocalDataSource.ImagePartDbModelLocalDataSource
import com.example.todolist.data.parts.partsLocalDataSource.entities.ImagePartDbModel
import com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.imagePartDataDao.entities.ImageContent
import com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.imagePartDataDao.entities.ImageMetaInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImagePartDataDao @Inject constructor(
    private val imageContentDao: ImageContentDao,
    private val imageMetaInfoDao: ImageMetaInfoDao,
) : ImagePartDbModelLocalDataSource {

    override fun getImagePartsOfTask(taskId: Long, appContext: Context): Flow<List<ImagePartDbModel>> {
        return imageMetaInfoDao.getImageMetaInfoOfTask(taskId).map { list ->
            list.map { imageMetaInfo ->
                val imageContent = imageContentDao.loadPhotoFromInternalStorage(
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
    }

    override suspend fun addImagePart(imagePart: ImagePartDbModel, appContext: Context): Long {
        val imageMetaInfo = ImageMetaInfo(imagePart.position, imagePart.parentId)
        val id = imageMetaInfoDao.addImageMetaInfo(imageMetaInfo)
        val imageContent = ImageContent(id, imagePart.content)
        imageContentDao.savePhotoToInternalStorage(imageContent, appContext)
        return id
    }

    override suspend fun deleteImagePart(imagePart: ImagePartDbModel, appContext: Context): Unit {
        val imageMetaInfo = ImageMetaInfo(imagePart.position, imagePart.parentId)
        val imageContent = ImageContent(imagePart.id, imagePart.content)
        imageMetaInfoDao.deleteImageMetaInfo(imageMetaInfo)
        imageContentDao.deletePhotoFromInternalStorage(imageContent, appContext)
    }

}
