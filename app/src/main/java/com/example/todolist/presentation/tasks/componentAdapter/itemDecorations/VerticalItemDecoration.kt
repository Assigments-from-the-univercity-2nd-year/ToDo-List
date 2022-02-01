package com.example.todolist.presentation.tasks.componentAdapter.itemDecorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.util.dp

class VerticalItemDecoration(
    private val divider: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val oneSideVerticalDivider = divider / 2
        val currentPosition = parent.getChildAdapterPosition(view)
            .takeIf { it != RecyclerView.NO_POSITION }
            ?: return

        with(outRect) {
            top = if (isFirstView(currentPosition)) oneSideVerticalDivider.dp else divider.dp
            bottom = oneSideVerticalDivider.dp
        }
    }

    private fun isFirstView(currentPosition: Int): Boolean {
        return currentPosition != 0
    }

}
