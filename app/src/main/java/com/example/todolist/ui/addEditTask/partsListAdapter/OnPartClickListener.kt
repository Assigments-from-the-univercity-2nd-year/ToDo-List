package com.example.todolist.ui.addEditTask.partsListAdapter

import com.example.todolist.ui.entities.BasePart

interface OnPartClickListener {
    fun onPartContentChanged(part: BasePart)
}