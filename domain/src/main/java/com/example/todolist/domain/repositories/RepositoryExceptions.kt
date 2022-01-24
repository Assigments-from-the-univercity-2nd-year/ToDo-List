package com.example.todolist.domain.repositories

sealed class RepositoryExceptions : Throwable() {

    data class CantFetchFilterPreferencesException(override val cause: Throwable?) : RepositoryExceptions()

    data class UnknownException(override val cause: Throwable?) : RepositoryExceptions()

}
