package com.example.todolist.data.partsDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob
import java.util.*

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
    override val id: Long = 0
) : TaskPart()

@Entity
data class TodoDataPart(
    override val content: String,
    override val position: Int,
    val parentId: Long,
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0
) : TaskPart()

class TodoPart(private val todoDataPart: TodoDataPart) {
    val content: List<String>
        get() = todoDataPart.content.split("\n")
}

/*@Entity
data class ImagePart(
    override val content: Blob,
    override val position: Int,
    val parentId: Long,
    @PrimaryKey(autoGenerate = true)
    override val id: Long
) : TaskPart()*/