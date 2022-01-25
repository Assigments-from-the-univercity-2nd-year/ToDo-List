package com.example.todolist.presentation.deleteFolder

import androidx.lifecycle.ViewModel
import com.example.todolist.di.ApplicationScope
import com.example.todolist.domain.useCases.folderUseCases.DeleteCompletedTasksOfFolderUseCase
import com.example.todolist.domain.useCases.folderUseCases.DeleteFolderUseCase
import com.example.todolist.presentation.entities.components.FolderUiState
import com.example.todolist.presentation.entities.components.mapToDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteFolderViewModel @Inject constructor(
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
