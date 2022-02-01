package com.example.todolist.presentation.tasks.componentAdapter.folder

import androidx.core.view.isVisible
import com.example.todolist.databinding.ItemFolderBinding
import com.example.todolist.presentation.entities.components.FolderUiState
import com.example.todolist.presentation.tasks.componentAdapter.ComponentViewHolder

class FolderViewHolder(
    binding: ItemFolderBinding,
    private val onFolderClicked: (FolderUiState) -> Unit,
) : ComponentViewHolder<ItemFolderBinding, FolderUiState>(binding) {

    override fun onBindViewHolder(component: FolderUiState) {
        binding.apply {
            textviewItemfolderFoldername.text = component.title
            textviewItemfolderNumberofitemsinfolder.text = component.numberOfSubComponents
            imageviewItemfolderPinning.isVisible = component.isPinned

            root.setOnClickListener {
                onFolderClicked(component)
            }
        }
    }
}
