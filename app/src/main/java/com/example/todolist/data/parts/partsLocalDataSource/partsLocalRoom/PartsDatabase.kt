package com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.data.parts.partsLocalDataSource.entities.ImagePartModel
import com.example.todolist.data.parts.partsLocalDataSource.entities.TextPartModel
import com.example.todolist.data.parts.partsLocalDataSource.entities.TodoPartModel
import com.example.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [TextPartModel::class, TodoPartModel::class, ImagePartModel::class], version = 1)
abstract class PartsDatabase : RoomDatabase() {
    abstract fun textPartDataDao(): TextPartDataDao
    abstract fun todoPartDataDao(): TodoPartDataDao
    abstract fun imagePartDataDao(): ImagePartDataDao

    class CallBack @Inject constructor(
        private val database: Provider<PartsDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val textPartDao = database.get().textPartDataDao()
            val todoPartDao = database.get().todoPartDataDao()

            applicationScope.launch {
                textPartDao.insertTextPartData(TextPartModel("You need to to your homework!!", 1, 1))
                textPartDao.insertTextPartData(TextPartModel("You need to to your homework!! - 2", 2, 1))
                textPartDao.insertTextPartData(TextPartModel("You need to to your homework!! - 3", 4, 1))

                todoPartDao.insertTodoPartData(TodoPartModel("Do 1\nDo 2\nDo sth else", 3, 1))
                todoPartDao.insertTodoPartData(TodoPartModel("Do 1\nDo 2\nDo sth else", 5, 1))
            }
        }
    }
}