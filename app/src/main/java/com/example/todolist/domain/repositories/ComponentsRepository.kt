package com.example.todolist.domain.repositories

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.Task
import com.example.todolist.util.Resource
import kotlinx.coroutines.flow.Flow

interface ComponentsRepository {

    suspend fun getRootFolder(): Resource<Folder>

    fun getSubFoldersOfFolder(folder: Folder): Resource<Flow<List<Folder>>>

    fun getTasksOfFolder(folder: Folder): Resource<Flow<List<Task>>>

    fun getPinnedFolders(): Resource<Flow<List<Folder>>>

    suspend fun addTask(task: Task): Resource<Long>

    suspend fun addFolder(folder: Folder): Resource<Long>

    suspend fun updateTask(task: Task): Resource<Unit>

    suspend fun updateFolder(folder: Folder): Resource<Unit>

    suspend fun deleteTask(task: Task): Resource<Unit>

    suspend fun deleteCompletedTasks(): Resource<Unit>

    suspend fun deleteFolder(folder: Folder): Resource<Unit>

}