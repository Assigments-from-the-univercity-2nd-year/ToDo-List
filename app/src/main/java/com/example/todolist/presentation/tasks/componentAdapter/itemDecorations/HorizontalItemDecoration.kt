package com.example.todolist.presentation.tasks.componentAdapter.itemDecorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.util.dp

class HorizontalItemDecoration(
    private val divider: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val oneSideHorizontalDivider = divider / 2

        with(outRect) {
            left = oneSideHorizontalDivider.dp
            right = oneSideHorizontalDivider.dp
        }
    }
}
