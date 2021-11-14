package com.example.todolist.ui.tasks.componentListAdapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.todolist.data.*
import com.example.todolist.databinding.ItemFolderBinding
import com.example.todolist.databinding.ItemTaskBinding

sealed class ComponentViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun onBindViewHolder(component: Component)

    class TaskViewHolder(
        private val binding: ItemTaskBinding,
        private val onComponentClickListener: OnComponentClickListener
    ) : ComponentViewHolder(binding) {

        override fun onBindViewHolder(component: Component) {
            val currentTask = component as Task
            binding.apply {
                checkboxItemtaskCompleted.isChecked = currentTask.isCompleted
                textviewItemtaskTaskname.text = currentTask.title
                textviewItemtaskModifieddate.text = currentTask.modifiedDateFormatted
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

                cardviewItemtaskCardview.elevation = 0F
                cardviewItemtaskCardview.radius = 48F
            }
        }

    }

    class FolderViewHolder(
        private val binding: ItemFolderBinding,
        private val onComponentClickListener: OnComponentClickListener
    ) : ComponentViewHolder(binding) {

        override fun onBindViewHolder(component: Component) {
            val currentFolder = component as Folder
            binding.apply {
                textviewItemfolderFoldername.text = currentFolder.title
                textviewItemfolderNumberofitemsinfolder.text = "(${currentFolder.numberOfSubComponents?.toString()})"
                imageviewItemfolderPinning.isVisible = currentFolder.isPinned

                root.setOnClickListener {
                    onComponentClickListener.onFolderClicked(currentFolder)
                }

                cardviewItemfolderCardview.elevation = 0F
                cardviewItemfolderCardview.radius = 48F
            }
        }

    }

}

