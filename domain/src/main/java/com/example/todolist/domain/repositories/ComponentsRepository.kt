package com.example.todolist.domain.repositories

import com.example.todolist.domain.models.components.Component
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.FolderCreatingDTO
import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ComponentsRepository {

    suspend fun getRootFolder(): Resource<Folder>

    fun getSubFoldersOfFolder(folder: Folder): Resource<Flow<List<Folder>>>

    suspend fun getParentFolderOfComponent(componentId: Long): Resource<Folder, Nothing, RepositoryExceptions>

    fun getTasksOfFolder(folder: Folder): Resource<Flow<List<Task>>>

    fun getPinnedFolders(): Resource<Flow<List<Folder>>>

    suspend fun addTask(task: Task): Resource<Long>

    suspend fun addFolder(folderCreatingDTO: FolderCreatingDTO): Resource<Long, Nothing, RepositoryExceptions>

    suspend fun updateTask(task: Task): Resource<Unit>

    suspend fun updateFolder(folder: Folder): Resource<Unit>

    suspend fun deleteTask(task: Task): Resource<Unit>

    suspend fun deleteCompletedTasks(): Resource<Unit>

    suspend fun deleteFolder(folder: Folder): Resource<Unit>

}