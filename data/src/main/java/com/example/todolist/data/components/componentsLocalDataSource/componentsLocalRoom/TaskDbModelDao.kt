package com.example.todolist.data.components.componentsLocalDataSource.componentsLocalRoom

import androidx.room.*
import com.example.todolist.data.components.componentsLocalDataSource.TaskLocalDataSource
import com.example.todolist.data.components.componentsLocalDataSource.entities.TaskDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDbModelDao : TaskLocalDataSource {

    @Query("SELECT * FROM taskdbmodel WHERE folderId = :folderId")
    override fun getTasksOfFolder(folderId: Long): Flow<List<TaskDbModel>>

    @Query("SELECT folderId FROM taskdbmodel WHERE id = :taskId")
    override suspend fun getParentFolderIdOfTask(taskId: Long): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun addTask(task: TaskDbModel): Long

    @Update
    override suspend fun updateTask(task: TaskDbModel)

    @Delete
    override suspend fun deleteTask(task: TaskDbModel)

    @Query("DELETE FROM taskdbmodel WHERE taskdbmodel.isCompleted = 1")
    override suspend fun deleteCompletedTasks()

}