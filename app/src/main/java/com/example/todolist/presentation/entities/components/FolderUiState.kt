package com.example.todolist.presentation.entities.components

import android.os.Parcelable
import com.example.todolist.presentation.entities.components.ComponentUiState
import kotlinx.android.parcel.Parcelize
import com.example.todolist.domain.models.components.Folder as DomainFolder

@Parcelize
data class FolderUiState(
    override val title: String,
    override val folderId: Long,
             val isPinned: Boolean,
    override val createdDate: Long,
    override val modifiedDate: Long,
    override val id: Long,
             val numberOfSubComponents: Int
) : ComponentUiState(title, folderId, createdDate, modifiedDate, id), Parcelable

fun FolderUiState.mapToDomain(): DomainFolder {
    TODO()
}

fun DomainFolder.mapToPresentation(): FolderUiState {
    TODO()
}