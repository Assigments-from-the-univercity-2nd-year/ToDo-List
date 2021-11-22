package com.example.todolist.data.partsDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [TextPart::class, TodoPart::class], version = 1)
abstract class PartDatabase :RoomDatabase() {
    abstract fun textPartDao(): TextPartDao
    abstract fun todoPartDao(): TodoPartDao
}