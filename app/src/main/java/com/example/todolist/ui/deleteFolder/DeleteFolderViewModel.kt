package com.example.todolist.ui.deleteFolder

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
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
    }

    fun onDeleteCompletedInFolderClicked(folder: Folder) = applicationScope.launch {
        folder.deleteCompleted(folderDao, taskDao)
    }
}