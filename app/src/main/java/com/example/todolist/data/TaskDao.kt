package com.example.todolist.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf

@Dao
interface TaskDao {

    fun getTasks(
        searchQuery: String,
        sortOrder: SortOrder,
        hideCompleted: Boolean
    ): Flow<List<Task>> =
        when (sortOrder) {
            SortOrder.BY_DATE -> getTasksSortedByDate(searchQuery, hideCompleted)
            SortOrder.BY_NAME -> getTasksSortedByName(searchQuery, hideCompleted)
        }

    fun getTasksOfFolder(
        searchQuery: String,
        sortOrder: SortOrder,
        hideCompleted: Boolean,
        folderId: Long
    ): Flow<List<Task>> {
        return when (sortOrder) {
            SortOrder.BY_DATE -> getImportantTasksOfFolderSortedByDate(searchQuery, hideCompleted, folderId)
            SortOrder.BY_NAME -> getImportantTasksOfFolderSortedByName(searchQuery, hideCompleted, folderId)
        }
    }

    @Query("SELECT * FROM task WHERE (isCompleted != :hideCompleted OR isCompleted = 0) AND title LIKE '%' || :searchQuery || '%' ORDER BY isImportant DESC, title")
    fun getTasksSortedByName(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE (isCompleted != :hideCompleted OR isCompleted = 0) AND title LIKE '%' || :searchQuery || '%' ORDER BY isImportant DESC, createdDate")
    fun getTasksSortedByDate(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE (isCompleted != :hideCompleted OR isCompleted = 0) AND title LIKE '%' || :searchQuery || '%' AND folderId = :folderId ORDER BY isImportant DESC, createdDate")
    fun getImportantTasksOfFolderSortedByName(searchQuery: String, hideCompleted: Boolean, folderId: Long): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE (isCompleted != :hideCompleted OR isCompleted = 0) AND title LIKE '%' || :searchQuery || '%' AND folderId = :folderId ORDER BY isImportant DESC, createdDate")
    fun getImportantTasksOfFolderSortedByDate(searchQuery: String, hideCompleted: Boolean, folderId: Long): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM task WHERE task.isCompleted = 1")
    suspend fun deleteCompletedTasks()
}