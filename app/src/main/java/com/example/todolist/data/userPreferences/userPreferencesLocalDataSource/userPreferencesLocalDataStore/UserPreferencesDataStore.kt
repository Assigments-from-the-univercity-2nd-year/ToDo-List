package com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.userPreferencesLocalDataStore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.UserPreferencesLocalDataSource
import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.entities.FilterPreferencesDbModel
import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.entities.SortOrderDbModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext context: Context
) : UserPreferencesLocalDataSource {

    private val dataStore = context.createDataStore("user_preferences")

    val preferencesFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val sortOrderDbModel = SortOrderDbModel.valueOf(
                preferences[PreferencesKeys.SORT_ORDER] ?: SortOrderDbModel.BY_DATE.name
            )
            val hideCompleted = preferences[PreferencesKeys.HIDE_COMPLETED] ?: false

            FilterPreferencesDbModel(sortOrderDbModel, hideCompleted)
        }

    override fun getFilterPreferencesDbModel(): Flow<FilterPreferencesDbModel> =
        preferencesFlow

    override suspend fun updateSortOrderDbModel(sortOrderModel: SortOrderDbModel) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SORT_ORDER] = sortOrderModel.name
        }
    }

    override suspend fun updateHideCompleted(hideCompleted: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.HIDE_COMPLETED] = hideCompleted
        }
    }

    private object PreferencesKeys {
        val SORT_ORDER = preferencesKey<String>("sort_order")
        val HIDE_COMPLETED = preferencesKey<Boolean>("hide_completed")
    }

    private companion object {
        private const val TAG = "PreferencesManager"
    }

}
