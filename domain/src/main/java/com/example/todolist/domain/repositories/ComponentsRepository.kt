package com.example.todolist.domain.repositories

import com.example.todolist.domain.models.components.Component
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.FolderCreatingDTO
import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ComponentsRepository {

    suspend fun getRootFolder(): Resource<Folder, RepositoryExceptions>

    fun getSubFoldersOfFolder(folder: Folder): Flow<Resource<List<Folder>, RepositoryExceptions>>

    suspend fun getParentFolderOfComponent(componentId: Long): Resource<Folder, RepositoryExceptions>

    fun getTasksOfFolder(folder: Folder): Flow<Resource<List<Task>, RepositoryExceptions>>

    fun getPinnedFolders(): Flow<Resource<List<Folder>, RepositoryExceptions>>

    suspend fun addTask(task: Task): Resource<Long, RepositoryExceptions>

    suspend fun addFolder(folderCreatingDTO: FolderCreatingDTO): Resource<Long, RepositoryExceptions>

    suspend fun updateTask(task: Task): Resource<Unit, RepositoryExceptions>

    suspend fun updateFolder(folder: Folder): Resource<Unit, RepositoryExceptions>

    suspend fun deleteTask(task: Task): Resource<Unit, RepositoryExceptions>

    suspend fun deleteCompletedTasks(): Resource<Unit, RepositoryExceptions>

    suspend fun deleteFolder(folder: Folder): Resource<Unit, RepositoryExceptions>

}