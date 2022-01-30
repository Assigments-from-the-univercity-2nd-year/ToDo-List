package com.example.todolist.presentation.entities.components

import com.example.todolist.domain.models.parts.Part

abstract class ComponentUiState(
    open val title: String,
    open val folderId: Long,
    open val createdDate: String,
    open val modifiedDate: String,
    open val id: Long
)
