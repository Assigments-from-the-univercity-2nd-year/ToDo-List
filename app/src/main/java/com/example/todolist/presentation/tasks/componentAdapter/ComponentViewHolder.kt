package com.example.todolist.presentation.tasks.componentAdapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.todolist.databinding.ItemFolderBinding
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.presentation.entities.components.ComponentUiState
import com.example.todolist.presentation.entities.components.FolderUiState
import com.example.todolist.presentation.entities.components.TaskUiState

abstract class ComponentViewHolder<out V : ViewBinding, in I : ComponentUiState>(
    protected val binding: V,
    //protected val onComponentClickListener: OnComponentClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun onBindViewHolder(component: I)

}

