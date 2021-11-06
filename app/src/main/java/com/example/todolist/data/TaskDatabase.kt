package com.example.todolist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class, Folder::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun folderDao(): FolderDao

    class CallBack @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val folderDao = database.get().folderDao()
            val taskDao = database.get().taskDao()

            applicationScope.launch {
                val rootFolder = folderDao.insertFolder(Folder("All", null))
                val folder = folderDao.insertFolder(Folder("Folder name", rootFolder))

                taskDao.insertTask(Task("Do your homework!", rootFolder))
                taskDao.insertTask(Task("Go get fruits", rootFolder, isCompleted = true))
                taskDao.insertTask(Task("Workout", rootFolder, isImportant = true))
                taskDao.insertTask(Task("Have a haircut", folder, isCompleted = true))
                taskDao.insertTask(Task("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM", rootFolder))
                taskDao.insertTask(Task("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM", rootFolder, isImportant = true))
            }
        }
    }
}