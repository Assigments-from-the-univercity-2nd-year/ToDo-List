package com.example.todolist.di

import android.app.Application
import androidx.room.Room
import com.example.todolist.data.componentsDB.TaskDatabase
import com.example.todolist.data.db.PartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application, callback: TaskDatabase.CallBack) =
        Room.databaseBuilder(app, TaskDatabase::class.java, "task_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Provides
    fun provideTaskDao(db: TaskDatabase) = db.taskDao()

    @Provides
    fun provideFolderDao(db: TaskDatabase) = db.folderDao()

    @Provides
    @Singleton
    fun providePartDatabase(app: Application, callback: PartDatabase.CallBack) =
        Room.databaseBuilder(app, PartDatabase::class.java, "part_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope