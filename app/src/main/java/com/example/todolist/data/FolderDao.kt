package com.example.todolist.data

import androidx.room.*

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
}