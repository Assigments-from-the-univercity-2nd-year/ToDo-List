package com.example.todolist.domain.models.components

data class FolderCreatingDTO(
    var title: String,
    var folderId: Long,
    val isPinned: Boolean
)