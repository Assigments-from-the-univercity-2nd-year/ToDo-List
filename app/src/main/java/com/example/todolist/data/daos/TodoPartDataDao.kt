package com.example.todolist.data.daos

import androidx.room.*
import com.example.todolist.data.entities.TodoPartData
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoPartDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoPartData(todoPartData: TodoPartData): Long

    @Update
    suspend fun updateTodoPartData(todoPartData: TodoPartData)

    @Delete
    suspend fun deleteTodoPartData(todoPartData: TodoPartData)

    @Query("SELECT * FROM todopartdata WHERE parentId = :taskId")
    fun getTodoPartDatasOfTask(taskId: Long): Flow<List<TodoPartData>>
}