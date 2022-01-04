package com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.entities

data class FilterPreferencesDbModel(
    val sortOrderModel: SortOrderDbModel,
    val hideCompleted: Boolean
)
