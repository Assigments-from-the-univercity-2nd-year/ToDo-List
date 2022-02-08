package com.example.todolist.presentation.addEditFolder

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.databinding.DialogFragmentAddEditFolderBinding
import com.example.todolist.di.ApplicationScope
import com.example.todolist.domain.useCases.folderUseCases.AddFolderUseCase
import com.example.todolist.domain.useCases.folderUseCases.AddFolderUseCase.AddFolderUseCaseException
import com.example.todolist.domain.useCases.folderUseCases.UpdateFolderUseCase
import com.example.todolist.domain.util.Resource
import com.example.todolist.presentation.ADD_FOLDER_RESULT_OK
import com.example.todolist.presentation.EDIT_FOLDER_RESULT_OK
import com.example.todolist.presentation.entities.components.FolderUiState
import com.example.todolist.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditFolderViewModel @Inject constructor(
    @ApplicationScope private val applicationScope: CoroutineScope,
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
        val isStarred: Boolean = binding.checkboxModalbottomsheetaddeditfolderPinning.isChecked
        val currentFolder: FolderUiState? = args.currentFolder

        applicationScope.launch {
            if (currentFolder == null) { // if is a new folder
                addNewFolder(folderName, isStarred, binding, args)
            } else {
                editFolder(folderName, isStarred, currentFolder.id, binding)
            }
        }
    }

    private suspend fun addNewFolder(
        folderName: String,
        isStarred: Boolean,
        binding: DialogFragmentAddEditFolderBinding,
        args: AddEditFolderDialogFragmentArgs
    ) {
        val addingResult = addFolderUseCase.invoke(
            title = folderName,
            parentFolderId = args.parentFolder.id,
            isStarred = isStarred
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
            }
        }.exhaustive
    }

    private suspend fun editFolder(
        folderTitle: String,
        isStarred: Boolean,
        folderId: Long,
        binding: DialogFragmentAddEditFolderBinding
    ) {
        val updatingResult = updateFolderUseCase(
            folderId,
            folderTitle,
            isStarred,
        )

        when (updatingResult) {
            is Resource.Success -> {
                addEditFolderEventChannel.send(
                    AddEditFolderEvent.NavigateBackWithResult(EDIT_FOLDER_RESULT_OK)
                )
            }
            is Resource.Failure -> {
                when (updatingResult.reason) {
                    UpdateFolderUseCase.UpdateFolderUseCaseException.BlankNameError -> {
                        binding.edittextModalbottomsheetaddeditfolderFoldername.error = "Name is blank!"
                    }
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
