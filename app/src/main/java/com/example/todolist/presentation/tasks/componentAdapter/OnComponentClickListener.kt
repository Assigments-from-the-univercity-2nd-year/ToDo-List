package com.example.todolist.presentation.tasks.componentAdapter

import com.example.todolist.presentation.entities.components.FolderUiState
import com.example.todolist.presentation.entities.components.TaskUiState

interface OnComponentClickListener {
    fun onFolderClicked(folder: FolderUiState)
    fun onTaskClicked(task: TaskUiState)
    fun onCheckBoxClicked(task: TaskUiState, isChecked: Boolean)
}