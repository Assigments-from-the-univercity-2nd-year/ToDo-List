package com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.imagePartDataDao.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageMetaInfo(
    val position: Int,
    val parentId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)