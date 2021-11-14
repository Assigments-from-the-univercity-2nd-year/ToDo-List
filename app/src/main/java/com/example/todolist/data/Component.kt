package com.example.todolist.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.first
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

sealed class Component {
    abstract val uniqueStringId: String
    abstract val modifiedDate: Long
    abstract val title: String?

    abstract suspend fun delete(folderDao: FolderDao, taskDao: TaskDao)
    abstract suspend fun deleteCompleted(folderDao: FolderDao, taskDao: TaskDao)
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

    override suspend fun delete(folderDao: FolderDao, taskDao: TaskDao) {
        for (task in taskDao.getTasksOfFolder(id).first()) {
            task.delete(folderDao, taskDao)
        }

        for (folder in folderDao.getFoldersOfFolder(id, "").first()) {
            folder.delete(folderDao, taskDao)
        }

        folderDao.deleteTask(this)
    }

    override suspend fun deleteCompleted(folderDao: FolderDao, taskDao: TaskDao) {

        for (component in taskDao.getCompletedTasksOfFolder(id).first()) {
            component.delete(folderDao, taskDao)
        }

        for (folder in folderDao.getFoldersOfFolder(id, "").first()) {
            folder.delete(folderDao, taskDao)
        }
    }

    suspend fun updateDate(folderDao: FolderDao) {
        folderDao.updateFolder(this.copy(modifiedDate = System.currentTimeMillis()))
    }

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
        get() = when (System.currentTimeMillis() - modifiedDate) {
            // less than 1 day
            in 0L..86_400_000L -> {
                SimpleDateFormat("h:mm a", Locale.getDefault()).format(modifiedDate)
            }
            // less than one year
            in 86_401L..31_556_926_000L -> {
                SimpleDateFormat("MMM d", Locale.getDefault()).format(modifiedDate)
            }
            else -> DateFormat.getDateInstance(DateFormat.MEDIUM).format(modifiedDate)
        }
    override val uniqueStringId: String
        get() = "0${id}"

    override suspend fun delete(folderDao: FolderDao, taskDao: TaskDao) {
        taskDao.deleteTask(this)
    }

    override suspend fun deleteCompleted(folderDao: FolderDao, taskDao: TaskDao) {
        if (isCompleted) {
            taskDao.deleteTask(this)
        }
    }

}