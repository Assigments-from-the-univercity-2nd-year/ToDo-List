package com.example.todolist.presentation.tasks.simpleCallbacks

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.componentsDB.Component
import com.example.todolist.data.componentsDB.Folder
import com.example.todolist.presentation.tasks.TasksViewModel
import com.example.todolist.presentation.tasks.componentListAdapter.ComponentViewHolder

class MovingSimpleCallback(
    private val taskAdapter: ListAdapter<Component, ComponentViewHolder>,
    private val viewModel: TasksViewModel
) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    0
) {
    private var fromPos: Int = -1
    private var toPos: Int = -1

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        toPos = target.adapterPosition
        fromPos = viewHolder.adapterPosition
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (fromPos != -1 && toPos != -1 && fromPos != toPos) {
            val component = taskAdapter.currentList[fromPos]
            val folder = taskAdapter.currentList[toPos] as? Folder
            viewModel.taskMovedToFolder(component, folder)
        }
    }

}
