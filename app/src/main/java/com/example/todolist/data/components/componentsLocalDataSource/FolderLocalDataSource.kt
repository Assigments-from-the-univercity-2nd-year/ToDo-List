package com.example.todolist.data.components.componentsLocalDataSource

import com.example.todolist.data.components.componentsLocalDataSource.entities.FolderDbModel
import kotlinx.coroutines.flow.Flow

interface FolderLocalDataSource {

    suspend fun getRootFolder(): FolderDbModel

    fun getPinnedFolders(): Flow<List<FolderDbModel>>

    fun getFoldersOfFolder(folderId: Long): Flow<List<FolderDbModel>>

    suspend fun addFolder(folder: FolderDbModel): Long

    suspend fun updateFolder(folder: FolderDbModel)

    suspend fun deleteFolder(folder: FolderDbModel)

}