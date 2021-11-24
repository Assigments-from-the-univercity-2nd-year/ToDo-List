package com.example.todolist.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TextPartData(
    val content: String,
    val position: Int,
    val parentId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
