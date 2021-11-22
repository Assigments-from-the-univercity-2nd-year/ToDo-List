package com.example.todolist.data.partsDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

sealed class TaskPart {
    abstract val content: Any
    abstract val position: Int
    abstract val id: Long
}

@Entity
data class TextPart(
    override val content: String,
    override val position: Int,
    val parentId: Long,
    @PrimaryKey(autoGenerate = true)
    override val id: Long
) : TaskPart()

@Entity
data class TodoPart(
    override val content: String,
    override val position: Int,
    val parentId: Long,
    @PrimaryKey(autoGenerate = true)
    override val id: Long
) : TaskPart()

/*@Entity
data class ImagePart(
    override val content: Blob,
    override val position: Int,
    val parentId: Long,
    @PrimaryKey(autoGenerate = true)
    override val id: Long
) : TaskPart()*/