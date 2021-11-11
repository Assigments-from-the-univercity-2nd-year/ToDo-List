package com.example.todolist.ui.deleteFolder

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.Folder
import com.example.todolist.data.FolderDao
import com.example.todolist.data.TaskDao
import com.example.todolist.di.ApplicationScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteFolderViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    private val folderDao: FolderDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun onDeleteFolderClicked(folder: Folder) = applicationScope.launch {
        folder.delete(folderDao, taskDao)
        if (folder.folderId != null) {
            val parentFolder = folderDao.getFolder(folder.folderId)
            updateFolderTime(parentFolder)
        }
    }

    fun onDeleteCompletedInFolderClicked(folder: Folder) = applicationScope.launch {
        folder.deleteCompleted(folderDao, taskDao)
        updateFolderTime(folder)
    }

    private fun updateFolderTime(folder: Folder) = viewModelScope.launch {
        folderDao.updateFolder(folder.copy(modifiedDate = System.currentTimeMillis()))
    }
}