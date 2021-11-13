package com.example.todolist.ui.addEditFolder

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.todolist.data.Folder
import com.example.todolist.data.FolderDao
import com.example.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AddEditFolderViewModel @ViewModelInject constructor(
    private val folderDao: FolderDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun applyFolder(folderName: String, parentFolder: Folder) = applicationScope.launch {
        folderDao.insertFolder(Folder(folderName, parentFolder.id))
        parentFolder.updateDate(folderDao)
    }

}