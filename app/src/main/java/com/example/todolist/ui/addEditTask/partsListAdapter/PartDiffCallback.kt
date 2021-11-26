package com.example.todolist.ui.addEditTask.partsListAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.ui.entities.BasePart

class PartDiffCallback : DiffUtil.ItemCallback<BasePart>() {
    override fun areItemsTheSame(oldItem: BasePart, newItem: BasePart): Boolean =
        oldItem.position == newItem.position

    override fun areContentsTheSame(oldItem: BasePart, newItem: BasePart): Boolean =
        oldItem == newItem
}