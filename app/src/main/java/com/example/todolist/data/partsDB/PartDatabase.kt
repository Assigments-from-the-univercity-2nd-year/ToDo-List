package com.example.todolist.data.partsDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TextPart::class, TodoPart::class], version = 1)
abstract class PartDatabase :RoomDatabase() {
    abstract fun textPartDao(): TextPartDao
    abstract fun todoPartDao(): TodoPartDao
}