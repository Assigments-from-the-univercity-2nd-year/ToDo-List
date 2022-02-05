package com.example.todolist.presentation.entities.components

import android.os.Parcelable
import com.example.todolist.presentation.entities.components.ComponentUiState
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import com.example.todolist.domain.models.components.Task as DomainTask

@Parcelize
data class TaskUiState(
    override val title: String = "New task",
    override val folderId: Long = 0,
             val isImportant: Boolean = false,
             val isCompleted: Boolean = false,
    override val createdDate: String = "date/time",
    override val modifiedDate: String = "date/time",
    override val id: Long = 0,
) : ComponentUiState(title, folderId, createdDate, modifiedDate, id), Parcelable

fun TaskUiState.mapToDomain(): DomainTask {
    TODO()
}

fun DomainTask.mapToPresentation(): TaskUiState {
    return TaskUiState(
        title = title,
        folderId = parentFolderId,
        isImportant = isImportant,
        isCompleted = isCompleted,
        createdDate = DateFormat.getDateTimeInstance().format(createdDate),
        modifiedDate = when (System.currentTimeMillis() - modifiedDate) {
            // less than 1 day
            in 0L..86_400_000L -> {
                SimpleDateFormat("h:mm a", Locale.getDefault()).format(modifiedDate)
            }
            // less than one year
            in 86_401L..31_556_926_000L -> {
                SimpleDateFormat("MMM d", Locale.getDefault()).format(modifiedDate)
            }
            else -> DateFormat.getDateInstance(DateFormat.MEDIUM).format(modifiedDate)
        },
        id = id
    )
}