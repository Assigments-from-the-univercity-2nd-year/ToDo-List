package com.example.todolist.data.userPreferences


import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.UserPreferencesLocalDataSource
import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.entities.FilterPreferencesDbModel
import com.example.todolist.domain.models.userPreferences.FilterPreferences
import com.example.todolist.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesLocalDataSource: UserPreferencesLocalDataSource
) : UserPreferencesRepository {

    override fun getFilterPreferences(): Flow<FilterPreferences> =
        userPreferencesLocalDataSource.getFilterPreferencesDbModel().map { it.mapToDomain() }

    private fun FilterPreferencesDbModel.mapToDomain(): FilterPreferences {
        TODO("Not yet implemented")
    }

}