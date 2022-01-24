package com.example.todolist.data.userPreferences

import android.util.Log
import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.UserPreferencesLocalDataSource
import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.entities.FilterPreferencesDbModel
import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.entities.SortOrderDbModel
import com.example.todolist.domain.models.userPreferences.FilterPreferences
import com.example.todolist.domain.models.userPreferences.SortOrder
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.repositories.UserPreferencesRepository
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesLocalDataSource: UserPreferencesLocalDataSource
) : UserPreferencesRepository {

    override fun getFilterPreferences(): Flow<Resource<FilterPreferences, RepositoryExceptions>> {
        return userPreferencesLocalDataSource.getFilterPreferencesDbModel()
            .map {
                Resource.Success(it.mapToDomain()) as Resource<FilterPreferences, RepositoryExceptions>
            }.catch { exception ->
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
            }
    }

    override suspend fun updateHideCompleted(hideCompleted: Boolean): Resource<Unit, RepositoryExceptions> {
        return try {
            Resource.Success(userPreferencesLocalDataSource.updateHideCompleted(hideCompleted))
        } catch (exception: Exception) {
            Resource.Failure(reason = RepositoryExceptions.UnknownException(exception.cause))
        }
    }

    override suspend fun updateSortOrderDbModel(sortOrder: SortOrder): Resource<Unit, RepositoryExceptions> {
        return try {
            Resource.Success(userPreferencesLocalDataSource.updateSortOrderDbModel(sortOrder.mapToData()))
        } catch (exception: Exception) {
            Resource.Failure(reason = RepositoryExceptions.UnknownException(exception.cause))
        }
    }

    private fun FilterPreferencesDbModel.mapToDomain(): FilterPreferences {
        TODO("Not yet implemented")
    }

    private fun SortOrder.mapToData(): SortOrderDbModel {
        TODO("Not yet implemented")
    }

    private companion object {
        private const val TAG = "UserPreferencesRepo"
    }

}