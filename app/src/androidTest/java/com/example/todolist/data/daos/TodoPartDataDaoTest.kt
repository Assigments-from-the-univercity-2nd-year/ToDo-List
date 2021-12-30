package com.example.todolist.data.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.todolist.data.local.partsDataSource.PartsDatabase
import com.example.todolist.data.local.partsDataSource.entities.TodoPart
import com.example.todolist.data.local.partsDataSource.daos.TodoPartDataDao
import com.example.todolist.getOrAwaitValue
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class TodoPartDataDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: PartsDatabase
    private lateinit var dao: TodoPartDataDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PartsDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.todoPartDataDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    private suspend fun addTodoPartDatasToDatabase() {
        dao.insertTodoPartData(TodoPart("a content", 1, 1L, false))
        dao.insertTodoPartData(TodoPart("a content", 2, 1L, false))
        dao.insertTodoPartData(TodoPart("a content", 1, 2L, false))
        dao.insertTodoPartData(TodoPart("a content", 3, 1L, false))
        dao.insertTodoPartData(TodoPart("a content", 1, 3L, false))
        dao.insertTodoPartData(TodoPart("a content", 5, 1L, false))
    }

    @Test
    fun getTodoPartDatasOfTask() = runBlocking {
        addTodoPartDatasToDatabase()

        val id = 1L
        val img = dao.getTodoPartsOfTask(id).asLiveData().getOrAwaitValue()

        Truth.assertThat(img.map { it.parentId == id }).doesNotContain(false)
    }
}