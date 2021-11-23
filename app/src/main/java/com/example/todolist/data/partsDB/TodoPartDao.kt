package com.example.todolist.data.partsDB

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform

@Dao
interface TodoPartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoPart(textPart: TextPart): Long

    @Update
    suspend fun updateTodoPart(textPart: TextPart)

    @Delete
    suspend fun deleteTodoPart(textPart: TextPart)

    fun getTodoPartsOfTask(taskId: Long): Flow<List<TodoPart>> =
        getTodoDataPartsOfTask(taskId).transform { list ->
            emit(list.map { TodoPart(it) })
        }

    @Query("SELECT * FROM tododatapart WHERE parentId = :taskId")
    fun getTodoDataPartsOfTask(taskId: Long): Flow<List<TodoDataPart>>
}