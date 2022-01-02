package com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom

import androidx.room.*
import com.example.todolist.data.parts.partsLocalDataSource.entities.TextPartModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TextPartDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTextPartData(textPartData: TextPartModel): Long

    @Update
    suspend fun updateTextPartData(textPartData: TextPartModel)

    @Delete
    suspend fun deleteTextPartData(textPartData: TextPartModel)

    @Query("SELECT * FROM textpart WHERE parentId = :taskId")
    fun getTextPartsOfTask(taskId: Long): Flow<List<TextPartModel>>
}