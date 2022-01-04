package com.example.todolist.data.parts.partsLocalDataSource.entities

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

data class ImagePartDbModel(
    val content: ByteArray,
    val position: Int,
    val parentId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long
)
