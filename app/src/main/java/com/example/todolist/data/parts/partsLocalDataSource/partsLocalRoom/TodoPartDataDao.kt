package com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom

import androidx.room.*
import com.example.todolist.data.parts.partsLocalDataSource.entities.TodoPartDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoPartDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoPartData(todoPartData: TodoPartDbModel): Long

    @Update
    suspend fun updateTodoPartData(todoPartData: TodoPartDbModel)

    @Delete
    suspend fun deleteTodoPartData(todoPartData: TodoPartDbModel)

    @Query("SELECT * FROM todopart WHERE parentId = :taskId")
    fun getTodoPartsOfTask(taskId: Long): Flow<List<TodoPartDbModel>>
}