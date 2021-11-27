package com.example.todolist.data.daos

import androidx.room.*
import com.example.todolist.data.entities.ImagePartData
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagePartDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImagePartData(imagePartData: ImagePartData): Long

    @Update
    suspend fun updateImagePartData(imagePartData: ImagePartData)

    @Delete
    suspend fun deleteImagePartData(imagePartData: ImagePartData)

    @Query("SELECT * FROM imagepartdata WHERE parentId = :taskId")
    fun getImagePartDatasOfTask(taskId: Long): Flow<List<ImagePartData>>
}