package com.example.todolist.data.userPreferences.userPreferencesLocalDataSource

import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.entities.FilterPreferencesDbModel
import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.entities.SortOrderDbModel
import kotlinx.coroutines.flow.Flow

interface UserPreferencesLocalDataSource {

    fun getFilterPreferencesDbModel(): Flow<FilterPreferencesDbModel>

    suspend fun updateHideCompleted(hideCompleted: Boolean)

    suspend fun updateSortOrderDbModel(sortOrderModel: SortOrderDbModel)

}