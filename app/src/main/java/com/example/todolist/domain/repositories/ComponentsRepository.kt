package com.example.todolist.domain.repositories

import com.example.todolist.domain.models.components.Component
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.Task
import kotlinx.coroutines.flow.Flow

interface ComponentsRepository {

    suspend fun getRootFolder(): Folder

    fun getSubFoldersOfFolder(folder: Folder): Flow<List<Folder>>

    fun getTasksOfFolder(folder: Folder): Flow<List<Task>>

    fun getPinnedFolders(): Flow<List<Folder>>

    suspend fun addTask(task: Task): Long

    suspend fun addFolder(folder: Folder): Long

    suspend fun updateTask(task: Task)

    suspend fun updateFolder(folder: Folder)

    suspend fun deleteTask(task: Task)

    suspend fun deleteCompletedTasks()

    suspend fun deleteFolder(folder: Folder)

}