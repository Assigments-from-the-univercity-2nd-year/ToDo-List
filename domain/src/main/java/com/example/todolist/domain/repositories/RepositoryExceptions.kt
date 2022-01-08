package com.example.todolist.domain.repositories

sealed class RepositoryExceptions : Exception() {

    object UnknownException : RepositoryExceptions()

}
