package com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.imagePartDataDao

import androidx.room.*
import com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.imagePartDataDao.entities.ImageMetaInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageMetaInfoDao {

    @Query("SELECT * FROM imagemetainfo WHERE id = :imageId")
    fun getImageMetaInfoOfTask(imageId: Long): Flow<List<ImageMetaInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImageMetaInfo(imageMetaInfo: ImageMetaInfo): Long

    @Delete
    suspend fun deleteImageMetaInfo(imageMetaInfo: ImageMetaInfo)

}
