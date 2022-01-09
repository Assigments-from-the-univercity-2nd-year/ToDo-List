package com.example.todolist.domain.repositories

import com.example.todolist.domain.models.userPreferences.FilterPreferences
import com.example.todolist.domain.models.userPreferences.SortOrder
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getFilterPreferences(): Flow<Resource<FilterPreferences, RepositoryExceptions>>

    suspend fun updateHideCompleted(hideCompleted: Boolean): Resource<Unit, RepositoryExceptions>

    suspend fun updateSortOrderDbModel(sortOrder: SortOrder): Resource<Unit, RepositoryExceptions>

}