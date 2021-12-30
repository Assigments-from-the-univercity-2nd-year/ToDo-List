package com.example.todolist.data.local.componentsDataSource.daos

import androidx.room.*
import com.example.todolist.data.componentsDB.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE (isCompleted != :hideCompleted OR isCompleted = 0) AND title LIKE '%' || :searchQuery || '%' AND folderId = :folderId")
    fun getTasksOfFolder(searchQuery: String, hideCompleted: Boolean, folderId: Long): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE isCompleted = 1  AND folderId = :folderId")
    fun getCompletedTasksOfFolder(folderId: Long): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE folderId = :folderId")
    fun getTasksOfFolder(folderId: Long): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM task WHERE task.isCompleted = 1")
    suspend fun deleteCompletedTasks()
}