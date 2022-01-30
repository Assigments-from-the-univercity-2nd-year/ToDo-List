package com.example.todolist.presentation.entities.components

import android.os.Parcelable
import com.example.todolist.presentation.entities.components.ComponentUiState
import kotlinx.android.parcel.Parcelize
import com.example.todolist.domain.models.components.Folder as DomainFolder

@Parcelize
data class FolderUiState(
    override val title: String = "New folder",
    override val folderId: Long = 0,
             val isPinned: Boolean = false,
    override val createdDate: Long = System.currentTimeMillis(),
    override val modifiedDate: Long = System.currentTimeMillis(),
    override val id: Long = 0,
             val numberOfSubComponents: Int = 0,
) : ComponentUiState(title, folderId, createdDate, modifiedDate, id), Parcelable

fun FolderUiState.mapToDomain(): DomainFolder {
    TODO()
}

fun DomainFolder.mapToPresentation(): FolderUiState {
    TODO()
}