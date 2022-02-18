package com.example.todolist.domain.models.userPreferences

/**
 * The class that stands for user preferences to safe
 */
class FilterPreferences(
    val sortOrder: SortOrder,
    val hideCompleted: Boolean
)