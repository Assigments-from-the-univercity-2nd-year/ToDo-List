package com.example.todolist.data.userPreferences.userPreferencesLocalDataSource

import com.example.todolist.domain.models.userPreferences.FilterPreferences
import com.example.todolist.domain.models.userPreferences.SortOrder
import kotlinx.coroutines.flow.Flow

interface UserPreferencesLocalDataSource {

    fun getFilterPreferencesDbModel(): Flow<FilterPreferences>

    suspend fun updateHideCompleted(hideCompleted: Boolean)
    suspend fun updateSortOrderDbModel(sortOrderModel: SortOrder)

}