package com.example.todolist.presentation.addEditFolder

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.databinding.DialogFragmentAddEditFolderBinding
import com.example.todolist.domain.models.components.FolderCreatingDTO
import com.example.todolist.domain.useCases.folderUseCases.AddFolderUseCase
import com.example.todolist.domain.useCases.folderUseCases.AddFolderUseCase.AddFolderUseCaseException
import com.example.todolist.domain.useCases.folderUseCases.UpdateFolderUseCase
import com.example.todolist.domain.util.Resource
import com.example.todolist.presentation.ADD_FOLDER_RESULT_OK
import com.example.todolist.presentation.EDIT_FOLDER_RESULT_OK
import com.example.todolist.presentation.entities.Folder
import com.example.todolist.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditFolderViewModel @Inject constructor(
    private val applicationScope: CoroutineScope,
    private val addFolderUseCase: AddFolderUseCase,
    private val updateFolderUseCase: UpdateFolderUseCase
) : ViewModel() {

    private val addEditFolderEventChannel = Channel<AddEditFolderEvent>()
    val addEditFolderEvent = addEditFolderEventChannel.receiveAsFlow()

    fun onApplyClicked(
        binding: DialogFragmentAddEditFolderBinding,
        args: AddEditFolderDialogFragmentArgs
    ) {

        val folderName: String = binding.edittextModalbottomsheetaddeditfolderFoldername.text.toString()
        val isPinned: Boolean = binding.checkboxModalbottomsheetaddeditfolderPinning.isChecked
        val currentFolder: Folder? = args.currentFolder

        applicationScope.launch {
            if (isNewFolder(currentFolder)) {
                addNewFolder(folderName, isPinned, binding, args)
            } else {
                editFolder(folderName, isPinned, currentFolder!!, binding)
            }
        }
    }

    private fun isNewFolder(currentFolder: Folder?): Boolean = currentFolder == null

    private suspend fun addNewFolder(
        folderName: String,
        isPinned: Boolean,
        binding: DialogFragmentAddEditFolderBinding,
        args: AddEditFolderDialogFragmentArgs
    ) {
        val addingResult = addFolderUseCase.invoke(
            FolderCreatingDTO(
                title = folderName,
                folderId = args.parentFolder.id,
                isPinned = isPinned
            )
        )

        when (addingResult) {
            is Resource.Success -> {
                addEditFolderEventChannel.send(
                    AddEditFolderEvent.NavigateBackWithResult(ADD_FOLDER_RESULT_OK)
                )
            }
            is Resource.Failure -> when (addingResult.reason) {
                AddFolderUseCaseException.BlankNameError ->
                    binding.edittextModalbottomsheetaddeditfolderFoldername.error = "Name is blank!"
                is AddFolderUseCaseException.CantFindInsertedFolderInDatabase ->
                    showInvalidInputMessage("Error: can't find inserted folder.")
                is AddFolderUseCaseException.CantFindParentFolderInDatabase ->
                    showInvalidInputMessage("Error: can't find parent folder for this folder.")
                is AddFolderUseCaseException.CantUpdateParentFolderInDatabase ->
                    showInvalidInputMessage("Error: can't update parent folder of created folder.")
            }
        }.exhaustive
    }

    private suspend fun editFolder(
        folderName: String,
        isPinned: Boolean,
        currentFolder: Folder,
        binding: DialogFragmentAddEditFolderBinding
    ) {
        val updatingResult = updateFolderUseCase.invoke(
            currentFolder.copy(title = folderName, isPinned = isPinned).mapToDomain()
        )

        when (updatingResult) {
            is Resource.Success -> {
                addEditFolderEventChannel.send(
                    AddEditFolderEvent.NavigateBackWithResult(EDIT_FOLDER_RESULT_OK)
                )
                TODO("Log")
            }
            is Resource.Error -> {
                if (updatingResult.exception is UpdateFolderUseCaseExceptions) {
                    when(updatingResult.exception as UpdateFolderUseCaseExceptions) {
                        is UpdateFolderUseCaseExceptions.BlankNameException -> {
                            binding.edittextModalbottomsheetaddeditfolderFoldername.error = "Name is blank!"
                            TODO("Log")
                        }
                    }
                } else {
                    // unexpected error
                    TODO("Log")
                }
            }
        }.exhaustive
    }

    fun hideKeyboard(view: View) {
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getInputMethodManager(view: View): InputMethodManager {
        val context = view.context
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addEditFolderEventChannel.send(AddEditFolderEvent.ShowInvalidInputMessage(text))
    }

    sealed class AddEditFolderEvent {
        data class NavigateBackWithResult(val result: Int) : AddEditFolderEvent()
        data class ShowInvalidInputMessage(val msg: String) : AddEditFolderEvent()
    }
}