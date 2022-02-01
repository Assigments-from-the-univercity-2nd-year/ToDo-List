package com.example.todolist.presentation.tasks.componentAdapter.folder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.todolist.R
import com.example.todolist.databinding.ItemFolderBinding
import com.example.todolist.presentation.entities.components.ComponentUiState
import com.example.todolist.presentation.entities.components.FolderUiState
import com.example.todolist.presentation.tasks.componentAdapter.ComponentFingerprint
import com.example.todolist.presentation.tasks.componentAdapter.ComponentViewHolder

class FolderFingerprint(
    private val onFolderClicked: (FolderUiState) -> Unit,
) : ComponentFingerprint<ItemFolderBinding, FolderUiState> {

    override fun isRelativeItem(item: ComponentUiState): Boolean {
        return item is FolderUiState
    }

    override fun getLayoutId(): Int {
        return R.layout.item_folder
    }

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ComponentViewHolder<ItemFolderBinding, FolderUiState> {
        val binding = ItemFolderBinding.inflate(layoutInflater, parent, false)
        return FolderViewHolder(binding, onFolderClicked)
    }
}
