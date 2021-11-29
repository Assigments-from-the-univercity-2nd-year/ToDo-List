package com.example.todolist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.data.Converters
import com.example.todolist.data.daos.ImagePartDataDao
import com.example.todolist.data.daos.TextPartDataDao
import com.example.todolist.data.daos.TodoPartDataDao
import com.example.todolist.data.entities.ImagePartData
import com.example.todolist.data.entities.TextPartData
import com.example.todolist.data.entities.TodoPartData
import com.example.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [TextPartData::class, TodoPartData::class, ImagePartData::class], version = 1)
@TypeConverters(Converters::class)
abstract class PartDatabase : RoomDatabase() {
    abstract fun textPartDataDao(): TextPartDataDao
    abstract fun todoPartDataDao(): TodoPartDataDao
    abstract fun imagePartDataDao(): ImagePartDataDao

    class CallBack @Inject constructor(
        private val database: Provider<PartDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val textPartDao = database.get().textPartDataDao()
            val todoPartDao = database.get().todoPartDataDao()

            applicationScope.launch {
                textPartDao.insertTextPartData(TextPartData("You need to to your homework!!", 1, 1))
                textPartDao.insertTextPartData(TextPartData("You need to to your homework!! - 2", 2, 1))
                textPartDao.insertTextPartData(TextPartData("You need to to your homework!! - 3", 4, 1))

                todoPartDao.insertTodoPartData(TodoPartData("Do 1\nDo 2\nDo sth else", 3, 1))
                todoPartDao.insertTodoPartData(TodoPartData("Do 1\nDo 2\nDo sth else", 5, 1))
            }
        }
    }
}