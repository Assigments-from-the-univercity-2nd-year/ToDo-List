package com.example.todolist.data.local.partsDataSource.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoPart(
    val content: String,
    val position: Int,
    val parentId: Long,
    val isCompleted: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
