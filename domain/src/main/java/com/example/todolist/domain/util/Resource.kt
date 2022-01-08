package com.example.todolist.domain.util

import kotlin.contracts.contract

/**
 * A sealed class that encapsulates a successful outcome with a value of type [S],
 * a domain error with a value of type [T] (recommended to use a sealed class)
 * or a failure with an arbitrary [Throwable] exception.
 */
sealed class Resource<out S : Any, out T : Any, out E : Throwable> {
    data class Success<out S : Any>(val data: S) : Resource<S, Nothing, Nothing>()
    data class Error<out T: Any>(val error: T) : Resource<Nothing, T, Nothing>()
    data class Exception<out E : Throwable>(val exception: E): Resource<Nothing, Nothing, E>()
}

fun <S : Any, T : Any, E : Throwable> Resource<S, T, E>.successOrReturn(
    onSuccess: S,
    onElse: E
): Any {

}

// assumeNoError
