package com.example.todolist.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Parcelize
@Entity
data class Task(
    val name: String,
    val isImportant: Boolean = false,
    val isCompleted: Boolean = false,
    val createdDate: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0 //TODO: ...=-1 not working -> only one entity was created
) : Parcelable {
    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(createdDate)
}
