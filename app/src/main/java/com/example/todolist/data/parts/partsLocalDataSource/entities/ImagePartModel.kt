package com.example.todolist.data.parts.partsLocalDataSource.entities

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity
data class ImagePartModel(
    val position: Int,
    val parentId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long
)
