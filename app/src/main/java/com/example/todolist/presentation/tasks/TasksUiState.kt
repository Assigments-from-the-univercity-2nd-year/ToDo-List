package com.example.todolist.presentation.tasks

import com.example.todolist.presentation.entities.components.ComponentUiState
import com.example.todolist.presentation.entities.components.FolderUiState

/**
 * This class stands for ui state of [TasksFragment]
 */
data class TasksUiState(
    /**
     * indicates if the loading is in prosses
     */
    val isLoading: Boolean = false,

    /**
     * represent the current state of FAB animation
     */
    val fabAnimation: FABAnimation = FABAnimation.DO_NOTHING,

    /**
     * this filed has information about folder which content is displayed
     */
    val folderData: FolderUiState? = null,

    /**
     * the sub-folders and sub-tasks of the [folderData][TasksUiState.folderData]
     */
    val components: List<ComponentUiState> = emptyList()
) {

    /**
     * This class represent the states of the FAB animation
     */
    enum class FABAnimation {
        SHOW_FABS,
        HIDE_FABS,
        DO_NOTHING
    }

}
