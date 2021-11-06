package com.example.todolist.ui.tasks.componentListAdapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.todolist.data.Component
import com.example.todolist.data.Folder
import com.example.todolist.data.Task
import com.example.todolist.databinding.ItemFolderBinding
import com.example.todolist.databinding.ItemTaskBinding

sealed class ComponentViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun onBindViewHolder(component: Component)

    class NoteViewHolder(
        private val binding: ItemTaskBinding,
        private val onComponentClickListener: OnComponentClickListener
    ) : ComponentViewHolder(binding) {

        override fun onBindViewHolder(component: Component) {
            val currentTask = component as Task
            binding.apply {
                checkboxItemtaskCompleted.isChecked = currentTask.isCompleted
                textviewItemtaskTaskname.text = currentTask.title
                textviewItemtaskTaskname.paint.isStrikeThruText = currentTask.isCompleted
                appCompatImageViewItemTaskPriority.isVisible = currentTask.isImportant

                root.setOnClickListener {
                    onComponentClickListener.onNoteClicked(currentTask)
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

        override fun onBindViewHolder(component: Component) {
            val currentFolder = component as Folder
            binding.apply {
                textviewItemfolderFoldername.text = currentFolder.title
                //TODO: set the number of notes in folder
                //TODO: show an icon if this folder is pinned

                root.setOnClickListener {
                    onComponentClickListener.onFolderClicked(currentFolder)
                }
            }
        }

    }

}

