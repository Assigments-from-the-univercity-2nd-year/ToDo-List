package com.example.todolist.data.components.componentsLocalDataSource.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskDbModel(
    val title: String,
    val parentFolderId: Long,
    val isImportant: Boolean = false,
    val isCompleted: Boolean = false,
    val createdDate: Long = System.currentTimeMillis(),
    val modifiedDate: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
