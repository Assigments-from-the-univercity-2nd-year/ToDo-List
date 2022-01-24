package com.example.todolist.presentation.addEditTask

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
sealed class AddEditTaskAction : Parcelable {

    @Parcelize
    data class AddTask(val parentFolderIdForTask: Long) : AddEditTaskAction()

    @Parcelize
    data class EditTask(val taskId: Long) : AddEditTaskAction()

}
