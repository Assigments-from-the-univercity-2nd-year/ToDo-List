package com.example.todolist.data.userPreferences

import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.UserPreferencesLocalDataSource
import com.example.todolist.domain.models.userPreferences.FilterPreferences
import com.example.todolist.domain.models.userPreferences.SortOrder
import com.example.todolist.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesLocalDataSource: UserPreferencesLocalDataSource
) : UserPreferencesRepository {

    override fun getFilterPreferences(): Flow<FilterPreferences> {
        return userPreferencesLocalDataSource.getFilterPreferencesDbModel()
            /*.catch { exception ->
                Log.e(TAG, "Error reading preferences", exception)
                if (exception is IOException) {
                    emit(
                        Resource.Failure(
                            reason = RepositoryExceptions.CantFetchFilterPreferencesException(
                                exception.cause
                            )
                        )
                    )
                } else {
                    emit(Resource.Failure(reason = RepositoryExceptions.UnknownException(exception.cause)))
                }
            }*/
    }

    override suspend fun updateHideCompleted(hideCompleted: Boolean) {
        userPreferencesLocalDataSource.updateHideCompleted(hideCompleted)
    }

    override suspend fun updateSortOrderDbModel(sortOrder: SortOrder) {
        userPreferencesLocalDataSource.updateSortOrderDbModel(sortOrder)
    }

}