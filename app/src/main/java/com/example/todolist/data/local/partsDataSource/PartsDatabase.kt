package com.example.todolist.data.local.partsDataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.data.local.partsDataSource.daos.ImagePartDataDao
import com.example.todolist.data.local.partsDataSource.daos.TextPartDataDao
import com.example.todolist.data.local.partsDataSource.daos.TodoPartDataDao
import com.example.todolist.data.local.partsDataSource.entities.ImagePart
import com.example.todolist.data.local.partsDataSource.entities.TextPart
import com.example.todolist.data.local.partsDataSource.entities.TodoPart
import com.example.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [TextPart::class, TodoPart::class, ImagePart::class], version = 1)
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
                textPartDao.insertTextPartData(TextPart("You need to to your homework!!", 1, 1))
                textPartDao.insertTextPartData(TextPart("You need to to your homework!! - 2", 2, 1))
                textPartDao.insertTextPartData(TextPart("You need to to your homework!! - 3", 4, 1))

                todoPartDao.insertTodoPartData(TodoPart("Do 1\nDo 2\nDo sth else", 3, 1))
                todoPartDao.insertTodoPartData(TodoPart("Do 1\nDo 2\nDo sth else", 5, 1))
            }
        }
    }
}