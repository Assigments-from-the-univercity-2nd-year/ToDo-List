package com.example.todolist.ui.deleteFolder

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.todolist.data.TaskDao
import com.example.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteFolderViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun onDeleteFolderClicked() = applicationScope.launch {
// TODO: 07.11.2021  
    }

    fun onDeleteCompletedInFolderClicked() = applicationScope.launch {
// TODO: 07.11.2021
    }
}