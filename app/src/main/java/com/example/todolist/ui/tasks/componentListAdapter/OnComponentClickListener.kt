package com.example.todolist.ui.tasks.componentListAdapter

import com.example.todolist.data.Folder
import com.example.todolist.data.Task

interface OnComponentClickListener {
    fun onFolderClicked(folder: Folder)
    fun onNoteClicked(task: Task)
    fun onCheckBoxClicked(task: Task, isChecked: Boolean)
}