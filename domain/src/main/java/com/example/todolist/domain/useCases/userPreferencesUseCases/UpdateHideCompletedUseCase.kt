package com.example.todolist.domain.useCases.userPreferencesUseCases

import com.example.todolist.domain.repositories.UserPreferencesRepository
import javax.inject.Inject

class UpdateHideCompletedUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(hideCompleted: Boolean) {
        userPreferencesRepository.updateHideCompleted(hideCompleted)
    }

}
