package com.example.todolist.data.local.componentsDataSource.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    val title: String?,
    val folderId: Long,
    val isImportant: Boolean = false,
    val isCompleted: Boolean = false,
    val createdDate: Long = System.currentTimeMillis(),
    val modifiedDate: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
