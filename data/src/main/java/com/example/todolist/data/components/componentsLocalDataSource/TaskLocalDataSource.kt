package com.example.todolist.data.components.componentsLocalDataSource

import com.example.todolist.data.components.componentsLocalDataSource.entities.TaskDbModel
import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {

    fun getSubTasksFlow(parentFolderId: Long): Flow<List<TaskDbModel>>

    suspend fun getSubTasks(parentFolderId: Long): List<TaskDbModel>
    suspend fun getTask(taskId: Long): TaskDbModel

    suspend fun addTask(task: TaskDbModel): Long

    suspend fun updateTask(task: TaskDbModel)

    suspend fun deleteTask(taskId: Long)
    suspend fun deleteCompletedTasks()

}