package com.example.todolist.domain.repositories

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.Task
import kotlinx.coroutines.flow.Flow

/**
 * This is a components interface to the data layer
 */
interface ComponentsRepository {

    /**
     * This method returns a flow of folders which are sub-folders for the folder
     * which [id][Folder.id] is equal to [parentFolderId]
     *
     * @param parentFolderId an id of the parent folder
     *
     * @return a flow of sub-folders
     */
    fun getSubFoldersFlow(parentFolderId: Long): Flow<List<Folder>>

    /**
     * This method returns a flow of tasks which are sub-tasks for the folder
     * which [id][Task.id] is equal to [parentFolderId]
     *
     * @param parentFolderId an id of the parent folder
     *
     * @return a flow of sub-tasks
     */
    fun getSubTasksFlow(parentFolderId: Long): Flow<List<Task>>

    /**
     * This method returns a flow of folder which [id][Folder.id] is equal to [parentFolderId]
     *
     * @param folderId an id of the folder
     *
     * @return a flow of the folder
     */
    fun getFolderFlow(folderId: Long): Flow<Folder>

    /**
     * This method returns the root folder
     *
     * @return the root folder
     */
    suspend fun getRootFolder(): Folder

    /**
     * This method returns a list of starred folders
     *
     * @return a list of starred folders
     */
    suspend fun getStarredFolders(): List<Folder>

    /**
     * This method returns a task by its id
     *
     * @return a task
     */
    suspend fun getTask(taskId: Long): Task

    /**
     * This method returns a folder by its id
     *
     * @return a folder
     */
    suspend fun getFolder(folderId: Long): Folder

    /**
     * This method adds a task to the database
     *
     * @param task a task to insert
     *
     * @return [id][Task.id] of inserted task
     */
    suspend fun addTask(task: Task): Long

    /**
     * This method adds a folder to the database
     *
     * @param folder a folder to insert
     *
     * @return [id][Folder.id] of inserted task
     */
    suspend fun addFolder(folder: Folder): Long

    /**
     * This method updates an existing task. If the task does not exist than it would be added
     * to the database
     *
     * @param task a task to update
     */
    suspend fun updateTask(task: Task)

    /**
     * This method updates an existing folder. If the folder does not exist than it would be added
     * to the database
     *
     * @param task a task to update
     */
    suspend fun updateFolder(folder: Folder)

    /**
     * This method deletes a task from the database
     *
     * @param taskId [id][Task.id]
     */
    suspend fun deleteTask(taskId: Long)

    /**
     * This method deletes all completed tasks from the database
     */
    suspend fun deleteCompletedTasks()

    /**
     * This method deletes a folder from the database
     *
     * @param folderId [id][Folder.id]
     */
    suspend fun deleteFolder(folderId: Long)

}