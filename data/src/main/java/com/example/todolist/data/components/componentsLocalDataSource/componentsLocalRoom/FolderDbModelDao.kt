package com.example.todolist.data.components.componentsLocalDataSource.componentsLocalRoom

import androidx.room.*
import com.example.todolist.data.components.componentsLocalDataSource.FolderLocalDataSource
import com.example.todolist.data.components.componentsLocalDataSource.entities.FolderDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDbModelDao : FolderLocalDataSource {

    @Query("SELECT * FROM folderdbmodel WHERE folderId IS NULL")
    override suspend fun getRootFolder(): FolderDbModel

    @Query("SELECT * FROM folderdbmodel WHERE isPinned = 1")
    override fun getPinnedFolders(): Flow<List<FolderDbModel>>

    @Query("SELECT * FROM folderdbmodel WHERE folderId = :folderId")
    override fun getFoldersOfFolder(folderId: Long): Flow<List<FolderDbModel>>

    @Query("SELECT folderId FROM folderdbmodel WHERE id = :folderId")
    override suspend fun getParentFolderIdOfFolder(folderId: Long): Long

    @Query("SELECT * FROM folderdbmodel WHERE folderId = :folderId")
    override suspend fun getFolder(folderId: Long): FolderDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun addFolder(folder: FolderDbModel): Long

    @Update
    override suspend fun updateFolder(folder: FolderDbModel)

    @Delete
    override suspend fun deleteFolder(folder: FolderDbModel)

}