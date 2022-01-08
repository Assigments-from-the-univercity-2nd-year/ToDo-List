package com.example.todolist.presentation.addEditTask.partsListAdapter

import com.example.todolist.presentation.entities.BasePart
import com.example.todolist.presentation.entities.TodoPart

interface OnPartClickListener {
    fun onPartContentChanged(part: BasePart, newContent: String)
    fun onTodoPartCheckBoxClicked(todoPart: TodoPart, isChecked: Boolean)

    fun onDeleteActionSelected(part: BasePart)
    fun onMoveUpActionSelected(positionOfMovingPart: Int)
    fun onMoveDownActionSelected(positionOfMovingPart: Int)
}