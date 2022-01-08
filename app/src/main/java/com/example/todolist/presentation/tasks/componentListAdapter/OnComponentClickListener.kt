package com.example.todolist.presentation.tasks.componentListAdapter

import com.example.todolist.data.componentsDB.Folder
import com.example.todolist.data.componentsDB.Task

interface OnComponentClickListener {
    fun onFolderClicked(folder: Folder)
    fun onTaskClicked(task: Task)
    fun onCheckBoxClicked(task: Task, isChecked: Boolean)
}