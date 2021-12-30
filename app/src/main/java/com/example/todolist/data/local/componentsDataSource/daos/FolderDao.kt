package com.example.todolist.data.local.componentsDataSource.daos

import androidx.room.*
import com.example.todolist.data.componentsDB.Folder
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder: Folder): Long

    @Update
    suspend fun updateFolder(folder: Folder)

    @Delete
    suspend fun deleteTask(folder: Folder)

    @Query("SELECT * FROM folder WHERE folderId IS NULL")
    suspend fun getRootFolder(): Folder

    @Query("SELECT * FROM folder WHERE folderId = :folderId AND title LIKE '%' || :searchQuery || '%'")
    fun getFoldersOfFolder(folderId: Long, searchQuery: String): Flow<List<Folder>>

    @Query("SELECT * FROM folder WHERE folderId = :folderId")
    suspend fun getFoldersOfFolder(folderId: Long): List<Folder>

    @Query("SELECT * FROM folder WHERE id = :id")
    suspend fun getFolder(id: Long): Folder

    @Query("SELECT * FROM folder WHERE isPinned = 1")
    suspend fun getPinnedFolders(): List<Folder>
}