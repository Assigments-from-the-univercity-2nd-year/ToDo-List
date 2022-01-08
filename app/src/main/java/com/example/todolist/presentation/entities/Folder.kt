package com.example.todolist.presentation.entities

import com.example.todolist.domain.models.components.Folder as DomainFolder

data class Folder(
    var title: String,
    var folderId: Long,
    val isPinned: Boolean,
    val createdDate: Long,
    var modifiedDate: Long,
    val id: Long,
    val numberOfSubComponents: Int
) {
    fun mapToDomain(): DomainFolder {
        TODO()
    }
}