package com.example.todolist.presentation.delteAllCompeted

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.todolist.data.components.componentsLocalDataSource.componentsLocalRoom.TaskDbModelDao
import com.example.todolist.di.ApplicationScope
import com.example.todolist.domain.useCases.folderUseCases.DeleteCompletedTasksOfFolderUseCase
import com.example.todolist.domain.useCases.folderUseCases.GetRootFolderUseCase
import com.example.todolist.domain.util.onFailure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteAllCompletedViewModel @ViewModelInject constructor(
    private val deleteCompletedTasksOfFolderUseCase: DeleteCompletedTasksOfFolderUseCase,
    private val getRootFolderUseCase: GetRootFolderUseCase,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun onConfirmClicked() = applicationScope.launch {
        val rootFolder = getRootFolderUseCase().onFailure { TODO() }
        deleteCompletedTasksOfFolderUseCase(rootFolder)
    }
}