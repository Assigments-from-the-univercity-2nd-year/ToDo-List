package com.example.todolist.data.parts.partsLocalDataSource

import com.example.todolist.data.parts.partsLocalDataSource.entities.TodoPartDbModel
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface TodoPartDbModelLocalDataSource {

    fun getTodoPartOfTask(taskId: Long): Flow<List<TodoPartDbModel>>

    suspend fun addTodoPart(todoPart: TodoPartDbModel): Long

    suspend fun updateTodoPart(todoPart: TodoPartDbModel): Unit

    suspend fun deleteTodoPart(todoPart: TodoPartDbModel): Unit

}