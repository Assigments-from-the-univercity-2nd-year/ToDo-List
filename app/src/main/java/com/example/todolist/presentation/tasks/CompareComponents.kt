package com.example.todolist.presentation.tasks

import com.example.todolist.presentation.entities.components.ComponentUiState
import com.example.todolist.presentation.entities.components.TaskUiState

class CompareComponents {
    companion object : Comparator<ComponentUiState> {
        override fun compare(o1: ComponentUiState?, o2: ComponentUiState?) = when {
            (o1 as? TaskUiState)?.isImportant ?: false -> {
                if ((o2 as? TaskUiState)?.isImportant ?: false) {
                    0
                } else {
                    -1
                }
            }
            (o2 as? TaskUiState)?.isImportant ?: false -> {
                1
            }
            else -> 0
        }

    }
}