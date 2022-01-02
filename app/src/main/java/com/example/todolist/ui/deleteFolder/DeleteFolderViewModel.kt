package com.example.todolist.ui.deleteFolder

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.todolist.data.componentsDB.Folder
import com.example.todolist.data.components.componentsLocalDataSource.componentsLocalRoom.FolderDbModelDao
import com.example.todolist.data.components.componentsLocalDataSource.componentsLocalRoom.TaskDbModelDao
import com.example.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteFolderViewModel @ViewModelInject constructor(
    private val taskDao: TaskDbModelDao,
    private val folderDao: FolderDbModelDao,
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