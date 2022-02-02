package com.example.todolist.presentation.tasks.simpleCallbacks

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.presentation.tasks.TasksViewModel

class SwipingSimpleCallback(
    private val fingerprintsWithAction: List<TasksViewModel.FingerprintsWithAction>,
) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.ACTION_STATE_IDLE, // No dirs
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
        fingerprintsWithAction.find { it.fingerprint.getLayoutId() == viewHolder.itemViewType }
            ?.let { it.onSwipe(viewHolder.adapterPosition) }
            ?: throw IllegalArgumentException("View type not found at position ${viewHolder.adapterPosition}")
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = 0.3f

}
