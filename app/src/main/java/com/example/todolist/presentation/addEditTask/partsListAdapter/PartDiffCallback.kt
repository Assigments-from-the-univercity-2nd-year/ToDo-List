
package com.example.todolist.presentation.addEditTask.partsListAdapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.presentation.entities.parts.PartUiState

class PartDiffCallback : DiffUtil.ItemCallback<PartUiState>() {
    override fun areItemsTheSame(oldItem: PartUiState, newItem: PartUiState): Boolean =
        oldItem.position == newItem.position

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: PartUiState, newItem: PartUiState): Boolean =
        oldItem == newItem
}
