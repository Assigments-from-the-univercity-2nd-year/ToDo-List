package com.example.todolist.data.components.componentsLocalDataSource.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FolderDbModel(
    val title: String,
    val folderId: Long?,
    val isPinned: Boolean = false,
    val modifiedDate: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
