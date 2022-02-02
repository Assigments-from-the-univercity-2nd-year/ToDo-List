package com.example.todolist.domain.useCases.userPreferencesUseCases

import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.repositories.UserPreferencesRepository
import com.example.todolist.domain.util.Resource
import javax.inject.Inject

class UpdateHideCompletedUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(hideCompleted: Boolean): Resource<Unit, RepositoryExceptions> {
        return userPreferencesRepository.updateHideCompleted(hideCompleted)
    }

}
