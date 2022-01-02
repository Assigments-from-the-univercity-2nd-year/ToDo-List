package com.example.todolist.data.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.PartsDatabase
import com.example.todolist.data.parts.partsLocalDataSource.entities.TextPartModel
import com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.TextPartDataDao
import com.example.todolist.getOrAwaitValue
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TextPartDataDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: PartsDatabase
    private lateinit var dao: TextPartDataDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PartsDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.textPartDataDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    private suspend fun addTextPartDatasToDatabase() {
        dao.insertTextPartData(TextPartModel("a name", 1, 1L))
        dao.insertTextPartData(TextPartModel("a name", 2, 1L))
        dao.insertTextPartData(TextPartModel("a name", 5, 1L))
        dao.insertTextPartData(TextPartModel("a name", 1, 2L))
        dao.insertTextPartData(TextPartModel("a name", 1, 3L))
        dao.insertTextPartData(TextPartModel("a name", 2, 2L))
        dao.insertTextPartData(TextPartModel("a name", 6, 1L))
    }

    @Test
    fun getTextPartDatasOfTask() = runBlocking {
        addTextPartDatasToDatabase()

        val id = 1L
        val img = dao.getTextPartsOfTask(id).asLiveData().getOrAwaitValue()

        Truth.assertThat(img.map { it.parentId == id }).doesNotContain(false)
    }
}