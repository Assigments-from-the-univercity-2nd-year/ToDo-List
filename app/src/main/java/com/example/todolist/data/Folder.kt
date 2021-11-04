package com.example.todolist.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Folder(
    val title: String,
    val folderId: Long?,
    val isPinned: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
) : Parcelable
