package com.example.todolist.ui.tasks.componentListAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.data.Component
import com.example.todolist.data.Folder
import com.example.todolist.data.Task

class ItemDiffCallback : DiffUtil.ItemCallback<Component>() {
    override fun areItemsTheSame(oldItem: Component, newItem: Component): Boolean {
        return oldItem.uniqueStringId == newItem.uniqueStringId
    }

    override fun areContentsTheSame(oldItem: Component, newItem: Component): Boolean {
        return when (oldItem) {
            is Folder -> {
                if (newItem is Folder) {
                    oldItem == newItem
                } else {
                    false
                }
            }
            is Task -> {
                if (newItem is Task) {
                    oldItem == newItem
                } else {
                    false
                }
            }
        }
    }
}