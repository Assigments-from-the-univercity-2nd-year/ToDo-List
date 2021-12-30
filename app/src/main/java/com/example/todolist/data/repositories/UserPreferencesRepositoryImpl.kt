package com.example.todolist.data.repositories

import com.example.todolist.domain.models.FilterPreferences
import com.example.todolist.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class UserPreferencesRepositoryImpl : UserPreferencesRepository {

    override fun getFilterPreferences(): Flow<FilterPreferences> {
        TODO("Not yet implemented")
    }

}