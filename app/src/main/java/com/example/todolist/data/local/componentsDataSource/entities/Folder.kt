package com.example.todolist.data.local.componentsDataSource.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Folder(
    val title: String,
    val folderId: Long?,
    val isPinned: Boolean = false,
    val modifiedDate: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
