package com.example.todolist.data.components.componentsLocalDataSource.componentsLocalRoom

import androidx.room.*
import com.example.todolist.data.components.componentsLocalDataSource.TaskLocalDataSource
import com.example.todolist.data.components.componentsLocalDataSource.entities.TaskDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDbModelDao : TaskLocalDataSource {

    @Query("SELECT * FROM taskdbmodel WHERE parentFolderId = :parentFolderId")
    override fun getSubTasksFlow(parentFolderId: Long): Flow<List<TaskDbModel>>

    @Query("SELECT * FROM taskdbmodel WHERE parentFolderId = :parentFolderId")
    override suspend fun getSubTasks(parentFolderId: Long): List<TaskDbModel>

    @Query("SELECT * FROM taskdbmodel WHERE id = :taskId")
    override suspend fun getTask(taskId: Long): TaskDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun addTask(task: TaskDbModel): Long

    @Update
    override suspend fun updateTask(task: TaskDbModel)

    @Query("DELETE FROM taskdbmodel WHERE id = :taskId")
    override suspend fun deleteTask(taskId: Long)

    @Query("DELETE FROM taskdbmodel WHERE taskdbmodel.isCompleted = 1")
    override suspend fun deleteCompletedTasks()

}
