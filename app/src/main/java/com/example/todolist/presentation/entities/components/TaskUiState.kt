package com.example.todolist.presentation.entities.components

import android.os.Parcelable
import com.example.todolist.presentation.entities.components.ComponentUiState
import kotlinx.android.parcel.Parcelize
import com.example.todolist.domain.models.components.Task as DomainTask

@Parcelize
data class TaskUiState(
    override val title: String = "New task",
    override val folderId: Long = 0,
             val isImportant: Boolean = false,
             val isCompleted: Boolean = false,
    override val createdDate: Long = System.currentTimeMillis(),
    override val modifiedDate: Long = System.currentTimeMillis(),
    override val id: Long = 0,
) : ComponentUiState(title, folderId, createdDate, modifiedDate, id), Parcelable

fun TaskUiState.mapToDomain(): DomainTask {
    TODO()
}

fun DomainTask.mapToPresentation(): TaskUiState {
    TODO()
}