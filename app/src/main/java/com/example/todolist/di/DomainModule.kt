package com.example.todolist.di

import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.repositories.UserPreferencesRepository
import com.example.todolist.domain.useCases.folderUseCases.AddFolderUseCase
import com.example.todolist.domain.useCases.folderUseCases.GetComponentsOfFolderUseCase
import com.example.todolist.domain.useCases.folderUseCases.UpdateFolderUseCase
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

    @Provides
    fun provideUpdateFolderUseCase(componentsRepository: ComponentsRepository): UpdateFolderUseCase =
        UpdateFolderUseCase(componentsRepository)

    @Provides
    fun provideGetComponentsOfFolderUseCase(
        componentsRepository: ComponentsRepository,
        userPreferencesRepository: UserPreferencesRepository
    ): GetComponentsOfFolderUseCase =
        GetComponentsOfFolderUseCase(componentsRepository, userPreferencesRepository)
}