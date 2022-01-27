package com.example.todolist.presentation.tasks.componentListAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.todolist.databinding.ItemFolderBinding
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.presentation.entities.components.ComponentUiState
import com.example.todolist.presentation.entities.components.FolderUiState
import com.example.todolist.presentation.entities.components.TaskUiState

class ComponentAdapter(
    private val onComponentClickListener: OnComponentClickListener,
) : ListAdapter<ComponentUiState, ComponentViewHolder>(ItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        return when(viewType) {
            TYPE_FOLDER -> {
                val binding: ItemFolderBinding =
                    ItemFolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ComponentViewHolder.FolderViewHolder(binding, onComponentClickListener)
            }
            TYPE_TASK -> {
                val binding: ItemTaskBinding =
                    ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ComponentViewHolder.TaskViewHolder(binding, onComponentClickListener)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        holder.onBindViewHolder(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is TaskUiState -> TYPE_TASK
            is FolderUiState -> TYPE_FOLDER
            else -> {throw Exception()}
        }
    }
}

private const val TYPE_FOLDER = 0
private const val TYPE_TASK = 1