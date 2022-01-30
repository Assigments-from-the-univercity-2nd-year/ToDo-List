/*
package com.example.todolist.presentation.tasks.simpleCallbacks

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.presentation.entities.components.ComponentUiState
import com.example.todolist.presentation.tasks.TasksViewModel
import com.example.todolist.presentation.tasks.componentAdapter.ComponentViewHolder

class SwipingSimpleCallback(
    private val taskAdapter: ListAdapter<ComponentUiState, ComponentViewHolder>,
    private val viewModel: TasksViewModel
) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val component = taskAdapter.currentList[viewHolder.adapterPosition]
        //viewModel.onComponentSwiped(component, viewHolder.adapterPosition)
    }

}*/
