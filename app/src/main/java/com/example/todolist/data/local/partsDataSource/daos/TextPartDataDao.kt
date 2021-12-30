package com.example.todolist.data.local.partsDataSource.daos

import androidx.room.*
import com.example.todolist.data.local.partsDataSource.entities.TextPart
import kotlinx.coroutines.flow.Flow

@Dao
interface TextPartDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTextPartData(textPartData: TextPart): Long

    @Update
    suspend fun updateTextPartData(textPartData: TextPart)

    @Delete
    suspend fun deleteTextPartData(textPartData: TextPart)

    @Query("SELECT * FROM textpart WHERE parentId = :taskId")
    fun getTextPartsOfTask(taskId: Long): Flow<List<TextPart>>
}