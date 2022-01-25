package com.example.todolist.data.parts.partsLocalDataSource.entities

import androidx.room.PrimaryKey

data class ImagePartDbModel(
    val content: ByteArray,
    val position: Int,
    val parentId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long
)
