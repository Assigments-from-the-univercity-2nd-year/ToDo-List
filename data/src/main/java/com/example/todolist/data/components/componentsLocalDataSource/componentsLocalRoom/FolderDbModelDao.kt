package com.example.todolist.data.components.componentsLocalDataSource.componentsLocalRoom

import androidx.room.*
import com.example.todolist.data.components.componentsLocalDataSource.FolderLocalDataSource
import com.example.todolist.data.components.componentsLocalDataSource.entities.FolderDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDbModelDao : FolderLocalDataSource {

    @Query("SELECT * FROM folderdbmodel WHERE id = parentFolderId")
    override suspend fun getRootFolder(): FolderDbModel

    @Query("SELECT * FROM folderdbmodel WHERE isPinned = 1")
    override suspend fun getPinnedFolders(): List<FolderDbModel>

    @Query("SELECT * FROM folderdbmodel WHERE parentFolderId = :parentFolderId AND parentFolderId != id")
    override fun getSubFoldersFlow(parentFolderId: Long): Flow<List<FolderDbModel>>

    @Query("SELECT * FROM folderdbmodel WHERE parentFolderId = :parentFolderId AND parentFolderId != id")
    override suspend fun getSubFolders(parentFolderId: Long): List<FolderDbModel>

    @Query("SELECT * FROM folderdbmodel WHERE id = :folderId")
    override fun getFolderFlow(folderId: Long): Flow<FolderDbModel>

    @Query("SELECT * FROM folderdbmodel WHERE parentFolderId = :folderId")
    override suspend fun getFolder(folderId: Long): FolderDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun addFolder(folder: FolderDbModel): Long

    @Update
    override suspend fun updateFolder(folder: FolderDbModel)

    @Query("DELETE FROM folderdbmodel WHERE id = :folderId")
    override suspend fun deleteFolder(folderId: Long)

}
