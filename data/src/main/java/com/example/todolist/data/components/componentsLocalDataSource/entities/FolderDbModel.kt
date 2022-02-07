package com.example.todolist.data.components.componentsLocalDataSource.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FolderDbModel(
    val title: String,
    val parentFolderId: Long,
    val isStarred: Boolean = false,
    val createdDate: Long = System.currentTimeMillis(),
    val modifiedDate: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
