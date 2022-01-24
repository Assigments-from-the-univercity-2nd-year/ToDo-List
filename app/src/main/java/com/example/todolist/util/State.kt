package com.example.todolist.util

sealed class State<out T : Any> {
    data class Success<out T : Any>(val data: T) : State<T>()
    object Loading : State<Nothing>()
    data class Failure<out T : Throwable>(val reason: T) : State<Nothing>()
}
