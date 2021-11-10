package com.example.todolist.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

sealed class Component {
    abstract val uniqueStringId: String
    abstract val modifiedDate: Long
    abstract val title: String?
}

@Parcelize
@Entity
data class Folder(
    override val title: String,
    val folderId: Long?,
    val isPinned: Boolean = false,
    override val modifiedDate: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
) : Component(), Parcelable {
    override val uniqueStringId: String
        get() = "1${id}"
}

@Parcelize
@Entity
data class Task(
    override val title: String?,
    val folderId: Long,
    val isImportant: Boolean = false,
    val isCompleted: Boolean = false,
    val createdDate: Long = System.currentTimeMillis(),
    override val modifiedDate: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
) : Component(), Parcelable {
    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(createdDate)
    val modifiedDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(modifiedDate)
    override val uniqueStringId: String
        get() = "0${id}"
}