package com.example.todolist.presentation.tasks.componentAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.example.todolist.databinding.ItemFolderBinding
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.presentation.entities.components.ComponentUiState
import com.example.todolist.presentation.entities.components.FolderUiState
import com.example.todolist.presentation.entities.components.TaskUiState

class ComponentAdapter(
    private val fingerprints: List<ComponentFingerprint<*, *>>,
    //private val onComponentClickListener: OnComponentClickListener,
) : ListAdapter<ComponentUiState, ComponentViewHolder<ViewBinding, ComponentUiState>>(
    ItemDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComponentViewHolder<ViewBinding, ComponentUiState> {
        val inflater = LayoutInflater.from(parent.context)
        return fingerprints.find { it.getLayoutId() == viewType }
            ?.getViewHolder(inflater, parent)
            ?.let { it as ComponentViewHolder<ViewBinding, ComponentUiState> }
            ?: throw IllegalArgumentException("View type not found: $viewType")
    }

    override fun onBindViewHolder(
        holder: ComponentViewHolder<ViewBinding, ComponentUiState>,
        position: Int
    ) {
        holder.onBindViewHolder(currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return fingerprints.find { it.isRelativeItem(item) }
            ?.getLayoutId()
            ?: throw IllegalArgumentException("View type not found: $item")
    }
}
