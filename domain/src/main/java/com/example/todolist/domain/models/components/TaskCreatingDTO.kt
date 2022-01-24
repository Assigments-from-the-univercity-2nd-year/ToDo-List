package com.example.todolist.domain.models.components

data class TaskCreatingDTO(
    var title: String = "New task",
    var folderId: Long,
    var isImportant: Boolean = false,
    var isCompleted: Boolean = false,
)
