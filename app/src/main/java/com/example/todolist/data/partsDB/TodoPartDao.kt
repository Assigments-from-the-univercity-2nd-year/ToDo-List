package com.example.todolist.data.partsDB

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoPartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoPart(textPart: TextPart): Long

    @Update
    suspend fun updateTodoPart(textPart: TextPart)

    @Delete
    suspend fun deleteTodoPart(textPart: TextPart)

    @Query("SELECT * FROM todopart WHERE parentId = :taskId")
    fun getTodoPartsOfTask(taskId: Long): Flow<List<TextPart>>
}