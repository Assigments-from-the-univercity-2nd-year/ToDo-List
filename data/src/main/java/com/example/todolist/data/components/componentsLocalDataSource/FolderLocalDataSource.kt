package com.example.todolist.data.components.componentsLocalDataSource

import com.example.todolist.data.components.componentsLocalDataSource.entities.FolderDbModel
import kotlinx.coroutines.flow.Flow

interface FolderLocalDataSource {

    suspend fun getRootFolder(): FolderDbModel?

    suspend fun getPinnedFolders(): List<FolderDbModel>

    fun getSubFoldersFlow(parentFolderId: Long): Flow<List<FolderDbModel>>

    suspend fun getSubFolders(parentFolderId: Long): List<FolderDbModel>

    fun getFolderFlow(folderId: Long): Flow<FolderDbModel>

    suspend fun getFolder(folderId: Long): FolderDbModel

    suspend fun addFolder(folder: FolderDbModel): Long

    suspend fun updateFolder(folder: FolderDbModel)

    suspend fun deleteFolder(folder: FolderDbModel)

}