package com.example.todolist.presentation.tasks.componentAdapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.presentation.entities.components.ComponentUiState
import com.example.todolist.presentation.entities.components.FolderUiState
import com.example.todolist.presentation.entities.components.TaskUiState

class ItemDiffCallback : DiffUtil.ItemCallback<ComponentUiState>() {

    override fun areItemsTheSame(oldItem: ComponentUiState, newItem: ComponentUiState): Boolean {
        return if (oldItem::class == newItem::class) {
            return oldItem.id == newItem.id
        } else {
            false
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ComponentUiState, newItem: ComponentUiState): Boolean {
        return if (oldItem::class == newItem::class) {
            return oldItem == newItem
        } else {
            false
        }
    }
}
