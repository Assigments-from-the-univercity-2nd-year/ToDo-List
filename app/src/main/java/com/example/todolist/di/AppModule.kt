package com.example.todolist.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.todolist.data.components.componentsLocalDataSource.componentsLocalRoom.ComponentsDatabase
import com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom.PartsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    /*@Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext appContext: Context) = appContext*/

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope