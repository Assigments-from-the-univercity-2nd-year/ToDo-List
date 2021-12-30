package com.example.todolist.data.local.partsDataSource.daos

import androidx.room.*
import com.example.todolist.data.local.partsDataSource.entities.ImagePart
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagePartDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImagePartData(imagePartData: ImagePart): Long

    @Update
    suspend fun updateImagePartData(imagePartData: ImagePart)

    @Delete
    suspend fun deleteImagePartData(imagePartData: ImagePart)

    @Query("SELECT * FROM imagepart WHERE parentId = :taskId")
    fun getImagePartsOfTask(taskId: Long): Flow<List<ImagePart>>
}