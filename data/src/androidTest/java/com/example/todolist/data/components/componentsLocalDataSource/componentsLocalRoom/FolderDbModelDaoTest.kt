package com.example.todolist.data.components.componentsLocalDataSource.componentsLocalRoom

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.todolist.data.components.componentsLocalDataSource.entities.FolderDbModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class FolderDbModelDaoTest {

    private lateinit var database: ComponentsDatabase
    private lateinit var folderDao: FolderDbModelDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ComponentsDatabase::class.java
        ).allowMainThreadQueries().build()
        folderDao = database.folderDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertFolder() = runBlocking {
        val folderInit = FolderDbModel(" a folder", 1L)
        val id = folderDao.addFolder(folderInit)
        val folder = folderInit.copy(id = id)

        val folderSearched = folderDao.getFolder(id)

        assertThat(folderSearched).isEqualTo(folder)
    }

    @Test
    fun updateFolder() = runBlocking {
        val folderInit = FolderDbModel(" a folder", 1L)
        val id = folderDao.addFolder(folderInit)
        val folder = folderInit.copy(id = id)
        val updatedFolder = folder.copy(title = "new folder name")
        folderDao.updateFolder(updatedFolder)

        val folderSearched = folderDao.getFolder(updatedFolder.id)

        assertThat(folderSearched).isEqualTo(updatedFolder)
    }

    @Test
    fun deleteTask() = runBlocking {
        val folderInit = FolderDbModel(" a folder", 1L)
        val id = folderDao.addFolder(folderInit)
        val folder = folderInit.copy(id = id)

        folderDao.deleteFolder(folder.id)

        val folderSearched = folderDao.getFolder(folder.id)

        assertThat(folderSearched).isNull()
    }

    @Test
    fun getRootFolder() = runBlocking {
        val folderInit = FolderDbModel("a root folder", 1L)
        val id = folderDao.addFolder(folderInit)

        val folderSearched = folderDao.getFolder(id)

        assertThat(folderSearched.parentFolderId).isEqualTo(1L)
    }

    @Test
    fun getStarredFolders() = runBlocking {
        folderDao.addFolder(FolderDbModel(" a folder", 1L))
        folderDao.addFolder(FolderDbModel(" a folder", 1, isStarred = true))
        folderDao.addFolder(FolderDbModel(" a folder", 1))
        folderDao.addFolder(FolderDbModel(" a folder", 2, isStarred = true))
        folderDao.addFolder(FolderDbModel(" a folder", 3))
        folderDao.addFolder(FolderDbModel(" a folder", 3, isStarred = true))
        folderDao.addFolder(FolderDbModel(" a folder", 3, isStarred = true))
        folderDao.addFolder(FolderDbModel(" a folder", 3))

        val pinnedFolders = folderDao.getStarredFolders()

        assertThat(pinnedFolders.map { it.isStarred }).doesNotContain(false)
    }

}