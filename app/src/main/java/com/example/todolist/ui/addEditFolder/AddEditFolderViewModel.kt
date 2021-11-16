package com.example.todolist.ui.addEditFolder

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.todolist.data.Folder
import com.example.todolist.data.FolderDao
import com.example.todolist.di.ApplicationScope
import com.example.todolist.ui.ADD_FOLDER_RESULT_OK
import com.example.todolist.ui.EDIT_FOLDER_RESULT_OK
import com.example.todolist.ui.addEditTask.AddEditTaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEditFolderViewModel @ViewModelInject constructor(
    private val folderDao: FolderDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    private val addEditFolderEventChannel = Channel<AddEditFolderEvent>()
    val addEditFolderEvent = addEditFolderEventChannel.receiveAsFlow()

    fun onApplyClicked(folderName: String, isPinned: Boolean, parentFolder: Folder, currentFolder: Folder?) {
        if (folderName.isBlank()) {
            // TODO: 16.11.2021 notify tha this is forbidden
            return
        }


        if (currentFolder != null) {
            val updatedFolder = currentFolder.copy(title = folderName, isPinned = isPinned)
            updateFolder(updatedFolder)
        } else {
            val newFolder = Folder(folderName, parentFolder.id, isPinned = isPinned)
            addFolder(newFolder)
        }

        applicationScope.launch {
            parentFolder.updateDate(folderDao)
        }
    }

    private fun addFolder(folder: Folder) = applicationScope.launch {
        folderDao.insertFolder(folder)
        addEditFolderEventChannel.send(AddEditFolderEvent.NavigateBackWithResult(ADD_FOLDER_RESULT_OK))

    }

    private fun updateFolder(folder: Folder) = applicationScope.launch {
        folderDao.updateFolder(folder)
        addEditFolderEventChannel.send(AddEditFolderEvent.NavigateBackWithResult(EDIT_FOLDER_RESULT_OK))
    }

    sealed class AddEditFolderEvent {
        data class NavigateBackWithResult(val result: Int) : AddEditFolderEvent()
    }
}