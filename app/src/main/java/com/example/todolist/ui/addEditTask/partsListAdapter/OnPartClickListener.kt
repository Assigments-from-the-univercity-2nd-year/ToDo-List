package com.example.todolist.ui.addEditTask.partsListAdapter

import com.example.todolist.ui.entities.BasePart
import com.example.todolist.ui.entities.TodoPart

interface OnPartClickListener {
    fun onPartContentChanged(part: BasePart, newContent: String)
    fun onTodoPartCheckBoxClicked(todoPart: TodoPart, isChecked: Boolean)

    fun onDeleteActionSelected(part: BasePart)
    fun onMoveUpActionSelected(positionOfMovingPart: Int)
    fun onMoveDownActionSelected(positionOfMovingPart: Int)
}