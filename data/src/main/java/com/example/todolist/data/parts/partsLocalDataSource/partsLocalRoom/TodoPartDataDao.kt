package com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom

import androidx.room.*
import com.example.todolist.data.parts.partsLocalDataSource.TodoPartDbModelLocalDataSource
import com.example.todolist.data.parts.partsLocalDataSource.entities.TodoPartDbModel
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoPartDataDao : TodoPartDbModelLocalDataSource {

    @Query("SELECT * FROM todopartdbmodel WHERE parentId = :taskId")
    override fun getTodoPartOfTask(taskId: Long): Flow<List<TodoPartDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun addTodoPart(todoPart: TodoPartDbModel): Long

    @Update
    override suspend fun updateTodoPart(todoPart: TodoPartDbModel): Unit

    @Delete
    override suspend fun deleteTodoPart(todoPart: TodoPartDbModel): Unit

}
