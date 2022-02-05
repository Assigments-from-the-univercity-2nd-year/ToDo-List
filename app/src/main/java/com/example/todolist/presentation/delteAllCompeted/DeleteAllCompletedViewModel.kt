package com.example.todolist.presentation.delteAllCompeted

import androidx.lifecycle.ViewModel
import com.example.todolist.di.ApplicationScope
import com.example.todolist.domain.useCases.folderUseCases.DeleteCompletedTasksOfFolderUseCase
import com.example.todolist.domain.useCases.folderUseCases.GetRootFolderUseCase
import com.example.todolist.domain.util.onFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAllCompletedViewModel @Inject constructor(
    private val deleteCompletedTasksOfFolderUseCase: DeleteCompletedTasksOfFolderUseCase,
    private val getRootFolderUseCase: GetRootFolderUseCase,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun onConfirmClicked() = applicationScope.launch {
        deleteCompletedTasksOfFolderUseCase(getRootFolderUseCase())
    }
}
