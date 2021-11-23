package com.example.todolist.data.partsDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [TextPart::class, TodoPart::class], version = 1)
abstract class PartDatabase : RoomDatabase() {
    abstract fun textPartDao(): TextPartDao
    abstract fun todoPartDao(): TodoPartDao

    class CallBack @Inject constructor(
        private val database: Provider<PartDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val textPartDao = database.get().textPartDao()

            applicationScope.launch {
                textPartDao.insertTextPart(TextPart("You need to to your homework!!", 1, 1))
                textPartDao.insertTextPart(TextPart("You need to to your homework!! - 2", 2, 1))
                textPartDao.insertTextPart(TextPart("You need to to your homework!! - 3", 3, 1))
            }
        }
    }
}