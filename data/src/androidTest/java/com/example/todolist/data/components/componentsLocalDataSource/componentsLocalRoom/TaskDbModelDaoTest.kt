package com.example.todolist.data.components.componentsLocalDataSource.componentsLocalRoom

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.todolist.data.components.componentsLocalDataSource.entities.TaskDbModel
import com.example.todolist.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TaskDbModelDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ComponentsDatabase
    private lateinit var taskDao: TaskDbModelDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ComponentsDatabase::class.java
        ).allowMainThreadQueries().build()
        taskDao = database.taskDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    private suspend fun addTasksToDatabase() {
        taskDao.addTask(TaskDbModel("a title of task", 1, true, true))
        taskDao.addTask(TaskDbModel("a title of task", 2, true, true))
        taskDao.addTask(TaskDbModel("", 2, true, false))
        taskDao.addTask(TaskDbModel("a title of task", 1, true, false))
        taskDao.addTask(TaskDbModel("of task", 1, false, true))
        taskDao.addTask(TaskDbModel("a of task", 3, true, true))
        taskDao.addTask(TaskDbModel("a title", 3, false, false))
        taskDao.addTask(TaskDbModel("a title of task", 3, false, false))
        taskDao.addTask(TaskDbModel("task", 1, false, true))
        taskDao.addTask(TaskDbModel("of task", 3, true, false))
        taskDao.addTask(TaskDbModel("a title of task", 2, true, false))
    }

    @Test
    fun getTasksOfFolder() = runBlocking {
        addTasksToDatabase()

        val folderId = 3L
        val tasksOfFolder = taskDao.getSubTasksFlow(folderId).asLiveData().getOrAwaitValue()

        assertThat(tasksOfFolder.map { it.parentFolderId == folderId }).doesNotContain(false)
    }

    @Test
    fun deleteCompletedTasks()= runBlocking {
        addTasksToDatabase()

        val folderId = 3L
        taskDao.deleteCompletedTasks()
        val tasksOfFolder = taskDao.getSubTasks(folderId)

        assertThat(tasksOfFolder.map { it.parentFolderId == folderId && it.isCompleted }).doesNotContain(true)
    }

}