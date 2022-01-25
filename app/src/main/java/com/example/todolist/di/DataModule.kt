package com.example.todolist.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.todolist.data.components.ComponentsRepositoryImpl
import com.example.todolist.data.components.componentsLocalDataSource.FolderLocalDataSource
import com.example.todolist.data.components.componentsLocalDataSource.TaskLocalDataSource
import com.example.todolist.data.components.componentsLocalDataSource.componentsLocalRoom.ComponentsDatabase
import com.example.todolist.data.parts.PartsRepositoryImpl
import com.example.todolist.data.parts.partsLocalDataSource.ImagePartDbModelLocalDataSource
import com.example.todolist.data.parts.partsLocalDataSource.TextPartDbModelLocalDataSource
import com.example.todolist.data.parts.partsLocalDataSource.TodoPartDbModelLocalDataSource
import com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.PartsDatabase
import com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.imagePartDataDao.ImageContentDao
import com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.imagePartDataDao.ImageMetaInfoDao
import com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.imagePartDataDao.ImagePartDataDao
import com.example.todolist.data.userPreferences.UserPreferencesRepositoryImpl
import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.UserPreferencesLocalDataSource
import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.userPreferencesLocalDataStore.UserPreferencesDataStore
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.domain.repositories.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    // userPreferences  ----------------------------------------------------------------------------

    @Provides
    @Singleton
    fun provideUserPreferencesLocalDataSource(
        @ApplicationContext appContext: Context
    ): UserPreferencesLocalDataSource =
        UserPreferencesDataStore(appContext)

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(
        userPreferencesLocalDataSource: UserPreferencesLocalDataSource
    ): UserPreferencesRepository =
        UserPreferencesRepositoryImpl(userPreferencesLocalDataSource)

    // components       ----------------------------------------------------------------------------

    @Provides
    @Singleton
    fun provideFolderLocalDataSource(
        componentsDatabase: ComponentsDatabase
    ): FolderLocalDataSource =
        componentsDatabase.folderDao()

    @Provides
    @Singleton
    fun provideTaskLocalDataSource(
        componentsDatabase: ComponentsDatabase
    ): TaskLocalDataSource =
        componentsDatabase.taskDao()

    @Provides
    @Singleton
    fun provideComponentsDatabase(
        app: Application,
        callback: ComponentsDatabase.CallBack
    ): ComponentsDatabase =
        Room.databaseBuilder(app, ComponentsDatabase::class.java, "task_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Provides
    @Singleton
    fun provideComponentsRepository(
        folderLocalDataSource: FolderLocalDataSource,
        taskLocalDataSource: TaskLocalDataSource
    ): ComponentsRepository =
        ComponentsRepositoryImpl(folderLocalDataSource, taskLocalDataSource)

    // parts            ----------------------------------------------------------------------------

    @Provides
    @Singleton
    fun provideImagePartDbModelLocalDataSource(
        imageContentDao: ImageContentDao,
        imageMetaInfoDao: ImageMetaInfoDao,
    ): ImagePartDbModelLocalDataSource =
        ImagePartDataDao(imageContentDao, imageMetaInfoDao)

    @Provides
    @Singleton
    fun provideImageContentDao(): ImageContentDao = ImageContentDao()

    @Provides
    @Singleton
    fun provideImageMetaInfoDao(
        partsDatabase: PartsDatabase
    ): ImageMetaInfoDao =
        partsDatabase.imageMetaInfoData()

    @Provides
    @Singleton
    fun provideITextPartDbModelLocalDataSource(
        partsDatabase: PartsDatabase
    ): TextPartDbModelLocalDataSource =
        partsDatabase.textPartDataDao()

    @Provides
    @Singleton
    fun provideTodoPartDbModelLocalDataSource(
        partsDatabase: PartsDatabase
    ): TodoPartDbModelLocalDataSource =
        partsDatabase.todoPartDataDao()

    @Provides
    @Singleton
    fun providePartDatabase(
        app: Application,
        callback: PartsDatabase.CallBack
    ): PartsDatabase =
        Room.databaseBuilder(app, PartsDatabase::class.java, "part_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Provides
    @Singleton
    fun providePartsRepository(
        textPartLocalDataSource: TextPartDbModelLocalDataSource,
        imagePartLocalDataSource: ImagePartDbModelLocalDataSource,
        todoPartLocalDataSource: TodoPartDbModelLocalDataSource,
        @ApplicationContext appContext: Context
    ): PartsRepository =
        PartsRepositoryImpl(
            textPartLocalDataSource,
            imagePartLocalDataSource,
            todoPartLocalDataSource,
            appContext
        )
}
