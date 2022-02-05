package com.example.todolist.domain.repositories

import com.example.todolist.domain.models.userPreferences.FilterPreferences
import com.example.todolist.domain.models.userPreferences.SortOrder
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getFilterPreferences(): Flow<FilterPreferences>

    suspend fun updateHideCompleted(hideCompleted: Boolean)

    suspend fun updateSortOrderDbModel(sortOrder: SortOrder)

}