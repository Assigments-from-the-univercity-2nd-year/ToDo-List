package com.example.todolist.presentation.tasks.componentAdapter.task

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.todolist.R
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.presentation.entities.components.ComponentUiState
import com.example.todolist.presentation.entities.components.TaskUiState
import com.example.todolist.presentation.tasks.componentAdapter.ComponentFingerprint
import com.example.todolist.presentation.tasks.componentAdapter.ComponentViewHolder

class TaskFingerprint(

) : ComponentFingerprint<ItemTaskBinding, TaskUiState> {
    override fun isRelativeItem(item: ComponentUiState): Boolean {
        return item is TaskUiState
    }

    override fun getLayoutId(): Int {
        return R.layout.item_task
    }

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ComponentViewHolder<ItemTaskBinding, TaskUiState> {
        val binding = ItemTaskBinding.inflate(layoutInflater, parent, false)
        return TaskViewHolder(binding)
    }
}