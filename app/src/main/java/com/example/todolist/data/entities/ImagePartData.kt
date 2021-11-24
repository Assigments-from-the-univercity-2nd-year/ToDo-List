package com.example.todolist.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity
data class ImagePartData(
    val content: Blob,
    val position: Int,
    val parentId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long
)
