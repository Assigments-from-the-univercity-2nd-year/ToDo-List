package com.example.todolist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton
/*
@Module
@InstallIn(AppCompo)
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
annotation class ApplicationScope*/