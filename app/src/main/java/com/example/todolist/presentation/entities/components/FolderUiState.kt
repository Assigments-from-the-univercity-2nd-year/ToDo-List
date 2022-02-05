package com.example.todolist.presentation.entities.components

import android.os.Parcelable
import com.example.todolist.presentation.entities.components.ComponentUiState
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import com.example.todolist.domain.models.components.Folder as DomainFolder

@Parcelize
data class FolderUiState(
    override val title: String = "New folder",
    override val folderId: Long = 0,
             val isPinned: Boolean = false,
    override val createdDate: String = "date/time",
    override val modifiedDate: String = "date/time",
    override val id: Long = 0,
             val numberOfSubComponents: String = "0",
) : ComponentUiState(title, folderId, createdDate, modifiedDate, id), Parcelable

fun FolderUiState.mapToDomain(): DomainFolder {
    TODO()
}

fun DomainFolder.mapToPresentation(): FolderUiState {
    return FolderUiState(
        title = title,
        folderId = parentFolderId,
        isPinned = isPinned,
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
        id = id,
        numberOfSubComponents = subComponents.size.toString()
    )
}