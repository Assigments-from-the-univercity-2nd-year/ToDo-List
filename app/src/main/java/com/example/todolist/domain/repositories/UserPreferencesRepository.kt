package com.example.todolist.domain.repositories

import com.example.todolist.domain.models.FilterPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getFilterPreferences(): Flow<FilterPreferences>

}