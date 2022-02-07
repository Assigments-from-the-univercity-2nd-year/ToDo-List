package com.example.todolist.presentation.entities.components

abstract class ComponentUiState(
    open val title: String,
    open val parentFolderId: Long,
    open val createdDate: String,
    open val modifiedDate: String,
    open val id: Long
)
