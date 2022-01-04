package com.example.todolist.data.parts.partsLocalDataSource.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TextPartDbModel(
    val content: String,
    val position: Int,
    val parentId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
