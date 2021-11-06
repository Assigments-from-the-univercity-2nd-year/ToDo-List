package com.example.todolist.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

sealed class Component {
    abstract val uniqueStringId: String
}

@Parcelize
@Entity
data class Folder(
    val title: String,
    val folderId: Long?,
    val isPinned: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
) : Component(), Parcelable {
    override val uniqueStringId: String
        get() = "1${id}"
}

@Parcelize
@Entity
data class Task(
    val title: String?,
    val folderId: Long,
    val isImportant: Boolean = false,
    val isCompleted: Boolean = false,
    val createdDate: Long = System.currentTimeMillis(),
    val modifiedDate: Long = System.currentTimeMillis(),
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