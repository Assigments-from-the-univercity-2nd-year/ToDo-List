package com.example.todolist.domain.useCases.userPreferencesUseCases

import com.example.todolist.domain.models.userPreferences.SortOrder
import com.example.todolist.domain.repositories.UserPreferencesRepository
import javax.inject.Inject

class UpdateSortOrderUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(sortOrder: SortOrder) {
        return userPreferencesRepository.updateSortOrderDbModel(sortOrder)
    }

}
