package com.example.todolist.presentation.tasks

import com.example.todolist.presentation.entities.components.ComponentUiState
import com.example.todolist.presentation.entities.components.FolderUiState

data class TasksUiState(
    val isLoading: Boolean = false,
    val folderData: FolderUiState? = null,
    val components: List<ComponentUiState> = emptyList()
)