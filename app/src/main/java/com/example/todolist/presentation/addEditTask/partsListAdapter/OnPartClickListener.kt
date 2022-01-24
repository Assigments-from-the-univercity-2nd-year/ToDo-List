package com.example.todolist.presentation.addEditTask.partsListAdapter

import com.example.todolist.presentation.entities.parts.PartUiState
import com.example.todolist.presentation.entities.parts.TodoPartUiState

interface OnPartClickListener {
    fun onPartContentChanged(part: PartUiState, newContent: String)
    fun onTodoPartCheckBoxClicked(todoPart: TodoPartUiState, isChecked: Boolean)

    fun onDeleteActionSelected(part: PartUiState)
    fun onMoveUpActionSelected(positionOfMovingPart: Int)
    fun onMoveDownActionSelected(positionOfMovingPart: Int)
}