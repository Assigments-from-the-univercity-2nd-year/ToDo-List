package com.example.todolist.data.components.componentsLocalDataSource

import com.example.todolist.data.components.componentsLocalDataSource.entities.TaskDbModel
import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {

    fun getTasksOfFolder(folderId: Long): Flow<List<TaskDbModel>>

    suspend fun addTask(task: TaskDbModel): Long

    suspend fun updateTask(task: TaskDbModel)

    suspend fun deleteTask(task: TaskDbModel)

    suspend fun deleteCompletedTasks()

}