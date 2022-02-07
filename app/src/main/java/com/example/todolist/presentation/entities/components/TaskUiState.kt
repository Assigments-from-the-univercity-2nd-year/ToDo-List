package com.example.todolist.presentation.entities.components

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.example.todolist.domain.models.components.Task as DomainTask

@Parcelize
data class TaskUiState(
    override val title: String = "New task",
    override val parentFolderId: Long = 0,
    val isImportant: Boolean = false,
    val isCompleted: Boolean = false,
    override val createdDate: String = "date/time",
    override val modifiedDate: String = "date/time",
    override val id: Long = 0,
) : ComponentUiState(title, parentFolderId, createdDate, modifiedDate, id), Parcelable

fun DomainTask.mapToPresentation(): TaskUiState {
    return TaskUiState(
        title = title,
        parentFolderId = parentFolderId,
        isImportant = isImportant,
        isCompleted = isCompleted,
        createdDate = createdDateFormatted,
        modifiedDate = modifiedDateFormatted,
        id = id
    )
}
