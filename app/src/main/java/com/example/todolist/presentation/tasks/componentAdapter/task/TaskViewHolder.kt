package com.example.todolist.presentation.tasks.componentAdapter.task

import androidx.core.view.isVisible
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.presentation.entities.components.TaskUiState
import com.example.todolist.presentation.tasks.componentAdapter.ComponentViewHolder

class TaskViewHolder(
    binding: ItemTaskBinding,
) : ComponentViewHolder<ItemTaskBinding, TaskUiState>(binding) {

    override fun onBindViewHolder(component: TaskUiState) {
        binding.apply {
            checkboxItemtaskCompleted.isChecked = component.isCompleted
            textviewItemtaskTaskname.text = component.title
            textviewItemtaskModifieddate.text = component.modifiedDate
            textviewItemtaskTaskname.paint.isStrikeThruText = component.isCompleted
            appCompatImageViewItemTaskPriority.isVisible = component.isImportant

            root.setOnClickListener {
                //onComponentClickListener.onTaskClicked(currentTask)
            }
            checkboxItemtaskCompleted.setOnClickListener {
                /*onComponentClickListener.onCheckBoxClicked(
                    currentTask,
                    checkboxItemtaskCompleted.isChecked
                )*/
            }
        }
    }

}
