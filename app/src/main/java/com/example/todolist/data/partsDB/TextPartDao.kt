package com.example.todolist.data.partsDB

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TextPartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTextPart(textPart: TextPart): Long

    @Update
    suspend fun updateTextPart(textPart: TextPart)

    @Delete
    suspend fun deleteTextPart(textPart: TextPart)

    @Query("SELECT * FROM textpart WHERE parentId = :taskId")
    fun getTextPartsOfTask(taskId: Long): Flow<List<TextPart>>
}