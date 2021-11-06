package com.example.todolist.ui.tasks.componentListAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.todolist.data.Component
import com.example.todolist.data.Folder
import com.example.todolist.data.Task
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
            TYPE_NOTE -> {
                val binding: ItemTaskBinding =
                    ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ComponentViewHolder.NoteViewHolder(binding, onComponentClickListener)
            }
            else -> throw Exception() //TODO("handling exception that it is not a folder or a note")
        }
    }

    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        holder.onBindViewHolder(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is Task -> TYPE_NOTE
            is Folder -> TYPE_FOLDER
        }
    }
}

private const val TYPE_FOLDER = 0
private const val TYPE_NOTE = 1