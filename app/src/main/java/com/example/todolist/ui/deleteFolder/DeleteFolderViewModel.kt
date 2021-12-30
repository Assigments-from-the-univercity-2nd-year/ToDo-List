package com.example.todolist.ui.deleteFolder

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.todolist.data.componentsDB.Folder
import com.example.todolist.data.local.componentsDataSource.daos.FolderDao
import com.example.todolist.data.local.componentsDataSource.daos.TaskDao
import com.example.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteFolderViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    private val folderDao: FolderDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun onDeleteFolderClicked(folder: Folder) = applicationScope.launch {
        folder.delete(folderDao, taskDao)
        if (folder.folderId != null) { // if it is not a root folder
            val parentFolder = folderDao.getFolder(folder.folderId)
            parentFolder.updateDate(folderDao)
        }
    }

    fun onDeleteCompletedInFolderClicked(folder: Folder) = applicationScope.launch {
        folder.deleteCompleted(folderDao, taskDao)
        folder.updateDate(folderDao)
    }

}