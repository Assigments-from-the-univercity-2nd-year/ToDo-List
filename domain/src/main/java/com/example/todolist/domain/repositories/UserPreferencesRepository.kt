package com.example.todolist.domain.repositories

import com.example.todolist.domain.models.userPreferences.FilterPreferences
import com.example.todolist.domain.models.userPreferences.SortOrder
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * This is a user preferences interface to the data layer
 */
interface UserPreferencesRepository {

    /**
     * This method returns a flow of [FilterPreferences]
     *
     * @return a flow of [FilterPreferences]
     */
    fun getFilterPreferences(): Flow<FilterPreferences>

    /**
     * This method is used to update [hideCompleted][FilterPreferences.hideCompleted] value
     *
     * @param hideCompleted does user want to hide the completed tasks
     */
    suspend fun updateHideCompleted(hideCompleted: Boolean)

    /**
     * This method is used to update [sortOrder][FilterPreferences.sortOrder] value
     *
     * @param sortOrder what sort order does user want to use
     */
    suspend fun updateSortOrderDbModel(sortOrder: SortOrder)

}