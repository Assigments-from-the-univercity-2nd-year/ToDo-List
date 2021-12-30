package com.example.todolist.data.local.partsDataSource.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TextPart(
    val content: String,
    val position: Int,
    val parentId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
