package com.example.todolist.data.components.componentsLocalDataSource.componentsLocalRoom

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.data.components.componentsLocalDataSource.entities.FolderDbModel
import com.example.todolist.data.components.componentsLocalDataSource.entities.TaskDbModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [TaskDbModel::class, FolderDbModel::class],
    version = 1,
    exportSchema = false,
)
abstract class ComponentsDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDbModelDao
    abstract fun folderDao(): FolderDbModelDao

    class CallBack @Inject constructor(
        private val database: Provider<ComponentsDatabase>,
        private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val folderDao = database.get().folderDao()
            val taskDao = database.get().taskDao()

            applicationScope.launch {
                val rootFolder = folderDao.addFolder(FolderDbModel("All", null))
                val folder = folderDao.addFolder(FolderDbModel("Folder name", rootFolder))

                taskDao.addTask(TaskDbModel("Do your homework!", rootFolder))
                taskDao.addTask(TaskDbModel("Go get fruits", rootFolder, isCompleted = true))
                taskDao.addTask(TaskDbModel("Workout", rootFolder, isImportant = true))
                taskDao.addTask(TaskDbModel("Have a haircut", folder, isCompleted = true))
                taskDao.addTask(TaskDbModel("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM", rootFolder))
                taskDao.addTask(TaskDbModel("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM", rootFolder, isImportant = true))
            }
        }
    }
}
