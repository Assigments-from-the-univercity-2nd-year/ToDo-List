package com.example.todolist.data.parts.partsLocalDataSource.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
data class ImagePartDbModel(
    val content: ByteArray,
    val position: Int,
    val parentId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long
)
