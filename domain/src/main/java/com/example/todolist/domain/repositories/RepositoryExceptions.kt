package com.example.todolist.domain.repositories

sealed class RepositoryExceptions : Throwable() {

    object UnknownException : RepositoryExceptions()

}
