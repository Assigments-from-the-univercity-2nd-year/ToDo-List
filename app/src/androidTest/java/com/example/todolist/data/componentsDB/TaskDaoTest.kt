package com.example.todolist.data.componentsDB

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.todolist.data.components.componentsLocalDataSource.componentsLocalRoom.ComponentsDatabase
import com.example.todolist.data.components.componentsLocalDataSource.componentsLocalRoom.TaskDbModelDao
import com.example.todolist.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TaskDaoTest {

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
        taskDao.insertTask(Task("a title of task", 1, true, true))
        taskDao.insertTask(Task("a title of task", 2, true, true))
        taskDao.insertTask(Task("", 2, true, false))
        taskDao.insertTask(Task("a title of task", 1, true, false))
        taskDao.insertTask(Task("of task", 1, false, true))
        taskDao.insertTask(Task("a of task", 3, true, true))
        taskDao.insertTask(Task("a title", 3, false, false))
        taskDao.insertTask(Task("a title of task", 3, false, false))
        taskDao.insertTask(Task("task", 1, false, true))
        taskDao.insertTask(Task("of task", 3, true, false))
        taskDao.insertTask(Task("a title of task", 2, true, false))
    }

    @Test
    fun getTasksOfFolder() = runBlocking {
        addTasksToDatabase()

        val folderId = 3L
        val tasksOfFolder =
            taskDao.getTasksOfFolder(folderId).asLiveData().getOrAwaitValue()

        assertThat(tasksOfFolder.map { it.folderId == folderId }).doesNotContain(false)
    }

    @Test
    fun getCompletedTasksOfFolder() = runBlocking {
        addTasksToDatabase()

        val folderId = 3L
        val tasksOfFolder =
            taskDao.getCompletedTasksOfFolder(folderId).asLiveData().getOrAwaitValue()

        assertThat(tasksOfFolder.map { it.folderId == folderId && it.isCompleted }).doesNotContain(false)
    }

    @Test
    fun deleteCompletedTasks()= runBlocking {
        addTasksToDatabase()

        val folderId = 3L
        val tasksOfFolder =
            taskDao.getCompletedTasksOfFolder(folderId).asLiveData().getOrAwaitValue()

        assertThat(tasksOfFolder.map { it.folderId == folderId && it.isCompleted }).doesNotContain(false)
    }
}