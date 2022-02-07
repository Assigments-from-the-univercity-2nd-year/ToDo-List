package com.example.todolist.domain.repositories

import com.example.todolist.domain.models.components.Component
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.FolderCreatingDTO
import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ComponentsRepository {

    fun getSubFoldersFlow(parentFolderId: Long): Flow<List<Folder>>
    fun getSubTasksFlow(parentFolderId: Long): Flow<List<Task>>
    fun getFolderFlow(folderId: Long): Flow<Folder>

    suspend fun getRootFolder(): Folder
    suspend fun getPinnedFolders(): List<Folder>

    suspend fun getTask(taskId: Long): Task
    suspend fun getFolder(folderId: Long): Folder

    suspend fun addTask(task: Task): Long
    suspend fun addFolder(folderCreatingDTO: FolderCreatingDTO): Long

    suspend fun updateTask(task: Task) //TODO: change argument to taskUpdatingDTO
    suspend fun updateFolder(folder: Folder) //TODO: change argument to folderUpdatingDTO

    suspend fun deleteTask(taskId: Long)
    suspend fun deleteCompletedTasks()
    suspend fun deleteFolder(folderId: Long)

}