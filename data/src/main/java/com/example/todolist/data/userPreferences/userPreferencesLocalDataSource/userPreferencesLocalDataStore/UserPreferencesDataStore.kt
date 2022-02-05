package com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.userPreferencesLocalDataStore

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.UserPreferencesLocalDataSource
import com.example.todolist.domain.models.userPreferences.FilterPreferences
import com.example.todolist.domain.models.userPreferences.SortOrder
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext context: Context
) : UserPreferencesLocalDataSource {

    private val dataStore = context.createDataStore("user_preferences")

    private val preferencesFlow = dataStore.data
        .map { preferences ->
            val sortOrderDbModel = SortOrder.valueOf(
                preferences[PreferencesKeys.SORT_ORDER] ?: SortOrder.BY_DATE.name
            )
            val hideCompleted = preferences[PreferencesKeys.HIDE_COMPLETED] ?: false

            FilterPreferences(sortOrderDbModel, hideCompleted)
        }

    override fun getFilterPreferencesDbModel(): Flow<FilterPreferences> =
        preferencesFlow

    override suspend fun updateSortOrderDbModel(sortOrderModel: SortOrder) {
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

}
