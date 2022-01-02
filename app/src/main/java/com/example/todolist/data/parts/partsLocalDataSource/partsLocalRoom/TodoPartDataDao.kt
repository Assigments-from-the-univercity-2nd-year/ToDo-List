package com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom

import androidx.room.*
import com.example.todolist.data.parts.partsLocalDataSource.entities.TodoPartModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoPartDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoPartData(todoPartData: TodoPartModel): Long

    @Update
    suspend fun updateTodoPartData(todoPartData: TodoPartModel)

    @Delete
    suspend fun deleteTodoPartData(todoPartData: TodoPartModel)

    @Query("SELECT * FROM todopart WHERE parentId = :taskId")
    fun getTodoPartsOfTask(taskId: Long): Flow<List<TodoPartModel>>
}