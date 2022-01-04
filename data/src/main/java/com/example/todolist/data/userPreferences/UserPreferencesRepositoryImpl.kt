package com.example.todolist.data.userPreferences

import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.UserPreferencesLocalDataSource
import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.entities.FilterPreferencesDbModel
import com.example.todolist.domain.models.userPreferences.FilterPreferences
import com.example.todolist.domain.models.userPreferences.SortOrder
import com.example.todolist.domain.repositories.UserPreferencesRepository
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesLocalDataSource: UserPreferencesLocalDataSource
) : UserPreferencesRepository {

    override fun getFilterPreferences(): Resource<Flow<FilterPreferences>> =
        Resource.Success( // TODO
            userPreferencesLocalDataSource.getFilterPreferencesDbModel().map { it.mapToDomain() }
        )

    override suspend fun updateHideCompleted(hideCompleted: Boolean): Resource<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateSortOrderDbModel(sortOrder: SortOrder): Resource<Unit> {
        TODO("Not yet implemented")
    }

    private fun FilterPreferencesDbModel.mapToDomain(): FilterPreferences {
        TODO("Not yet implemented")
    }

}