package com.example.todolist.ui.addEditTask.partsListAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.ui.entities.BasePart
import com.example.todolist.ui.entities.TextPart

class PartDiffCallback : DiffUtil.ItemCallback<BasePart>() {
    override fun areItemsTheSame(oldItem: BasePart, newItem: BasePart): Boolean =
        oldItem.hashCode() == newItem.hashCode()

    override fun areContentsTheSame(oldItem: BasePart, newItem: BasePart): Boolean =
        when(oldItem) {
            is TextPart -> {
                if (newItem is TextPart) {
                    (oldItem) == newItem
                } else {
                    false
                }
            }
            else -> false
        }
}