package com.example.todolist.data.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.todolist.data.local.partsDataSource.PartsDatabase
import com.example.todolist.data.local.partsDataSource.entities.ImagePart
import com.example.todolist.data.local.partsDataSource.daos.ImagePartDataDao
import com.example.todolist.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ImagePartDataDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: PartsDatabase
    private lateinit var dao: ImagePartDataDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PartsDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.imagePartDataDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    private suspend fun addImagePartDatasToDatabase() {
        dao.insertImagePartData(ImagePart(1, 1L, 0))
        dao.insertImagePartData(ImagePart(2, 1L, 0))
        dao.insertImagePartData(ImagePart(3, 1L, 0))
        dao.insertImagePartData(ImagePart(1, 2L, 0))
        dao.insertImagePartData(ImagePart(1, 3L, 0))
    }

    @Test
    fun getImagePartDatasOfTask() = runBlocking {
        addImagePartDatasToDatabase()

        val id = 1L
        val img = dao.getImagePartsOfTask(id).asLiveData().getOrAwaitValue()

        assertThat(img.map { it.parentId == id }).doesNotContain(false)
    }
}