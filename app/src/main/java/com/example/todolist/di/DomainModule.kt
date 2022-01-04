package com.example.todolist.di

import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.useCases.folderUseCases.AddFolderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    // folderUseCases   ----------------------------------------------------------------------------

    @Provides
    fun provideAddFolderUseCase(componentsRepository: ComponentsRepository): AddFolderUseCase =
        AddFolderUseCase(componentsRepository)

}