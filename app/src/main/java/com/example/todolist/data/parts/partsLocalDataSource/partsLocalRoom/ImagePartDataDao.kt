package com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom

import androidx.room.*
import com.example.todolist.data.parts.partsLocalDataSource.entities.ImagePartModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagePartDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImagePartData(imagePartData: ImagePartModel): Long

    @Update
    suspend fun updateImagePartData(imagePartData: ImagePartModel)

    @Delete
    suspend fun deleteImagePartData(imagePartData: ImagePartModel)

    @Query("SELECT * FROM imagepart WHERE parentId = :taskId")
    fun getImagePartsOfTask(taskId: Long): Flow<List<ImagePartModel>>
}