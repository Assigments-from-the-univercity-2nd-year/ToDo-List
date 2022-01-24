package com.example.todolist.presentation.addEditTask

import com.example.todolist.presentation.entities.components.TaskUiState
import com.example.todolist.presentation.entities.parts.PartUiState

data class AddEditTaskUiState (
    val isLoading: Boolean = false,
    val taskData: TaskUiState? = null,
    val parts: List<PartUiState> = emptyList()
)
