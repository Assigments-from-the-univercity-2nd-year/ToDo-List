package com.example.todolist.ui.addEditFolder

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.setFragmentResult
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.todolist.data.Folder
import com.example.todolist.data.FolderDao
import com.example.todolist.databinding.DialogFragmentAddEditFolderBinding
import com.example.todolist.di.ApplicationScope
import com.example.todolist.ui.ADD_FOLDER_RESULT_OK
import com.example.todolist.ui.EDIT_FOLDER_RESULT_OK
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

    fun onApplyClicked(
        binding: DialogFragmentAddEditFolderBinding,
        args: AddEditFolderDialogFragmentArgs
    ) {

        val folderName = binding.edittextModalbottomsheetaddeditfolderFoldername.text.toString()
        val isPinned = binding.checkboxModalbottomsheetaddeditfolderPinning.isChecked
        val currentFolder = args.currentFolder

        if (folderName.isBlank()) {
            binding.edittextModalbottomsheetaddeditfolderFoldername.error = "Name is blank!"
            return
        }

        if (currentFolder != null) { // if it is a new folder (creating folder)
            val updatedFolder = currentFolder.copy(title = folderName, isPinned = isPinned)
            updateFolder(updatedFolder)
        } else { // modifying folder
            val newFolder = Folder(folderName, args.parentFolder.id, isPinned = isPinned)
            addFolder(newFolder)
        }

        applicationScope.launch {
            args.parentFolder.updateDate(folderDao)
        }
    }

    fun getOnShowListener(binding: DialogFragmentAddEditFolderBinding) =
        DialogInterface.OnShowListener {
            binding.edittextModalbottomsheetaddeditfolderFoldername.requestFocus()
            showKeyboard(binding.edittextModalbottomsheetaddeditfolderFoldername)
        }

    fun hideKeyboard(view: View) {
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun addFolder(folder: Folder) = applicationScope.launch {
        folderDao.insertFolder(folder)
        addEditFolderEventChannel.send(
            AddEditFolderEvent.NavigateBackWithResult(ADD_FOLDER_RESULT_OK)
        )
    }

    private fun updateFolder(folder: Folder) = applicationScope.launch {
        folderDao.updateFolder(folder)
        addEditFolderEventChannel.send(
            AddEditFolderEvent.NavigateBackWithResult(EDIT_FOLDER_RESULT_OK)
        )
    }

    private fun getInputMethodManager(view: View): InputMethodManager {
        val context = view.context
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun showKeyboard(view: View) {
        view.post {
            getInputMethodManager(view).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    sealed class AddEditFolderEvent {
        data class NavigateBackWithResult(val result: Int) : AddEditFolderEvent()
    }
}