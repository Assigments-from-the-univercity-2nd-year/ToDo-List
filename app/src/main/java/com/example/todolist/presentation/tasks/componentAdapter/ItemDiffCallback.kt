package com.example.todolist.presentation.tasks.componentAdapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.presentation.entities.components.ComponentUiState
import com.example.todolist.presentation.entities.components.FolderUiState
import com.example.todolist.presentation.entities.components.TaskUiState

class ItemDiffCallback : DiffUtil.ItemCallback<ComponentUiState>() {
    override fun areItemsTheSame(oldItem: ComponentUiState, newItem: ComponentUiState): Boolean {
        return false//oldItem.uniqueStringId == newItem.uniqueStringId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ComponentUiState, newItem: ComponentUiState): Boolean {
        return when (oldItem) {
            is FolderUiState -> {
                if (newItem is FolderUiState) {
                    oldItem == newItem
                } else {
                    false
                }
            }
            is TaskUiState -> {
                if (newItem is TaskUiState) {
                    oldItem == newItem
                } else {
                    false
                }
            }
            else -> {true}
        }
    }
}