package com.example.todolist.presentation.deleteFolder

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.todolist.di.ApplicationScope
import com.example.todolist.domain.useCases.folderUseCases.DeleteCompletedTasksOfFolderUseCase
import com.example.todolist.domain.useCases.folderUseCases.DeleteFolderUseCase
import com.example.todolist.presentation.entities.FolderUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteFolderViewModel @ViewModelInject constructor(
    private val deleteFolderUseCase: DeleteFolderUseCase,
    private val deleteCompletedTasksOfFolderUseCase: DeleteCompletedTasksOfFolderUseCase,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun onDeleteFolderClicked(folder: FolderUiState) = applicationScope.launch {
        deleteFolderUseCase(folder.mapToDomain())
    }

    fun onDeleteCompletedInFolderClicked(folder: FolderUiState) = applicationScope.launch {
        deleteCompletedTasksOfFolderUseCase(folder.mapToDomain())
    }

}