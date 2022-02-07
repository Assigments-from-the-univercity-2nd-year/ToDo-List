package com.example.todolist.presentation.entities.components

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.example.todolist.domain.models.components.Folder as DomainFolder

@Parcelize
data class FolderUiState(
    override val title: String = "New folder",
    override val parentFolderId: Long = 0,
             val isPinned: Boolean = false,
    override val createdDate: String = "date/time",
    override val modifiedDate: String = "date/time",
    override val id: Long = 0,
             val numberOfSubComponents: String = "0",
) : ComponentUiState(title, parentFolderId, createdDate, modifiedDate, id), Parcelable {

}

fun DomainFolder.mapToPresentation(): FolderUiState {
    return FolderUiState(
        title = title,
        parentFolderId = parentFolderId,
        isPinned = isPinned,
        createdDate = createdDateFormatted,
        modifiedDate = modifiedDateFormatted,
        id = id,
        numberOfSubComponents = subComponents.size.toString()
    )
}
