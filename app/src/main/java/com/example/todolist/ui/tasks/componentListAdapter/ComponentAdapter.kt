package com.example.todolist.ui.tasks.componentListAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.todolist.data.componentsDB.Component
import com.example.todolist.data.componentsDB.Folder
import com.example.todolist.data.componentsDB.Task
import com.example.todolist.databinding.ItemFolderBinding
import com.example.todolist.databinding.ItemTaskBinding

class ComponentAdapter(
    private val onComponentClickListener: OnComponentClickListener,
) : ListAdapter<Component, ComponentViewHolder>(ItemDiffCallback()) {
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
            is Task -> TYPE_TASK
            is Folder -> TYPE_FOLDER
        }
    }
}

private const val TYPE_FOLDER = 0
private const val TYPE_TASK = 1