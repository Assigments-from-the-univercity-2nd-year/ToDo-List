package com.example.todolist.data.daos

import androidx.room.*
import com.example.todolist.data.entities.TextPartData
import kotlinx.coroutines.flow.Flow

@Dao
interface TextPartDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTextPartData(textPartData: TextPartData): Long

    @Update
    suspend fun updateTextPartData(textPartData: TextPartData)

    @Delete
    suspend fun deleteTextPartData(textPartData: TextPartData)

    @Query("SELECT * FROM textpartdata WHERE parentId = :taskId")
    fun getTextPartDatasOfTask(taskId: Long): Flow<List<TextPartData>>
}