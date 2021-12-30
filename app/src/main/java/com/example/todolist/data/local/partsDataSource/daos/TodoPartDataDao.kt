package com.example.todolist.data.local.partsDataSource.daos

import androidx.room.*
import com.example.todolist.data.local.partsDataSource.entities.TodoPart
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoPartDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoPartData(todoPartData: TodoPart): Long

    @Update
    suspend fun updateTodoPartData(todoPartData: TodoPart)

    @Delete
    suspend fun deleteTodoPartData(todoPartData: TodoPart)

    @Query("SELECT * FROM todopart WHERE parentId = :taskId")
    fun getTodoPartsOfTask(taskId: Long): Flow<List<TodoPart>>
}