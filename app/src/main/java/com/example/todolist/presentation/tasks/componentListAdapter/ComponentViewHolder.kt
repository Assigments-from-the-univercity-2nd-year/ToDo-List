package com.example.todolist.presentation.tasks.componentListAdapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.todolist.databinding.ItemFolderBinding
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.presentation.entities.components.ComponentUiState
import com.example.todolist.presentation.entities.components.FolderUiState
import com.example.todolist.presentation.entities.components.TaskUiState

sealed class ComponentViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun onBindViewHolder(component: ComponentUiState)

    class TaskViewHolder(
        private val binding: ItemTaskBinding,
        private val onComponentClickListener: OnComponentClickListener
    ) : ComponentViewHolder(binding) {

        override fun onBindViewHolder(component: ComponentUiState) {
            val currentTask = component as TaskUiState
            binding.apply {
                checkboxItemtaskCompleted.isChecked = currentTask.isCompleted
                textviewItemtaskTaskname.text = currentTask.title
                textviewItemtaskModifieddate.text = TODO() //currentTask.modifiedDateFormatted
                textviewItemtaskTaskname.paint.isStrikeThruText = currentTask.isCompleted
                appCompatImageViewItemTaskPriority.isVisible = currentTask.isImportant

                root.setOnClickListener {
                    onComponentClickListener.onTaskClicked(currentTask)
                }
                checkboxItemtaskCompleted.setOnClickListener {
                    onComponentClickListener.onCheckBoxClicked(
                        currentTask,
                        checkboxItemtaskCompleted.isChecked
                    )
                }

            }
        }

    }

    class FolderViewHolder(
        private val binding: ItemFolderBinding,
        private val onComponentClickListener: OnComponentClickListener
    ) : ComponentViewHolder(binding) {

        override fun onBindViewHolder(component: ComponentUiState) {
            val currentFolder = component as FolderUiState
            binding.apply {
                textviewItemfolderFoldername.text = currentFolder.title
                textviewItemfolderNumberofitemsinfolder.text = currentFolder.numberOfSubComponents?.toString()
                imageviewItemfolderPinning.isVisible = currentFolder.isPinned

                root.setOnClickListener {
                    onComponentClickListener.onFolderClicked(currentFolder)
                }

            }
        }

    }

}

