package com.example.todolist.data.componentsDB

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class FolderDaoTest {

    private lateinit var database: TaskDatabase
    private lateinit var folderDao: FolderDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TaskDatabase::class.java
        ).allowMainThreadQueries().build()
        folderDao = database.folderDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertFolder() = runBlocking {
        val folderInit = Folder(" a folder", null)
        val id = folderDao.insertFolder(folderInit)
        val folder = folderInit.copy(id = id)

        val folderSearched = folderDao.getFolder(id)

        assertThat(folderSearched).isEqualTo(folder)
    }

    @Test
    fun updateFolder() = runBlocking {
        val folderInit = Folder(" a folder", null)
        val id = folderDao.insertFolder(folderInit)
        val folder = folderInit.copy(id = id)
        val updatedFolder = folder.copy(title = "new folder name")
        folderDao.updateFolder(updatedFolder)

        val folderSearched = folderDao.getFolder(updatedFolder.id)

        assertThat(folderSearched).isEqualTo(updatedFolder)
    }

    @Test
    fun deleteTask() = runBlocking {
        val folderInit = Folder(" a folder", null)
        val id = folderDao.insertFolder(folderInit)
        val folder = folderInit.copy(id = id)

        folderDao.deleteTask(folder)

        val folderSearched = folderDao.getFolder(folder.id)

        assertThat(folderSearched).isNull()
    }

    @Test
    fun getRootFolder() = runBlocking {
        val folderInit = Folder("a root folder", null)
        val id = folderDao.insertFolder(folderInit)

        val folderSearched = folderDao.getFolder(id)

        assertThat(folderSearched.folderId).isNull()
    }

    @Test
    fun getPinnedFolders() = runBlocking {
        folderDao.insertFolder(Folder(" a folder", null))
        folderDao.insertFolder(Folder(" a folder", 1, isPinned = true))
        folderDao.insertFolder(Folder(" a folder", 1))
        folderDao.insertFolder(Folder(" a folder", 2, isPinned = true))
        folderDao.insertFolder(Folder(" a folder", 3))
        folderDao.insertFolder(Folder(" a folder", 3, isPinned = true))
        folderDao.insertFolder(Folder(" a folder", 3, isPinned = true))
        folderDao.insertFolder(Folder(" a folder", 3))

        val pinnedFolders = folderDao.getPinnedFolders()

        assertThat(pinnedFolders.map { it.isPinned }).doesNotContain(false)
    }
}