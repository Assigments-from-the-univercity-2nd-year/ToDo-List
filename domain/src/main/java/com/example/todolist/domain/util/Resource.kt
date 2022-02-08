package com.example.todolist.domain.util

/**
 * A sealed class that encapsulates a successful outcome with a value of type [T]
 * or a failure with an arbitrary [Exception] exception.
 */
sealed class Resource<out T : Any, out E : Exception> {
    data class Success<out T : Any>(val data: T) : Resource<T, Nothing>()
    data class Failure<out E: Exception>(val reason: E) : Resource<Nothing, E>()
}

/*inline fun <T : Any, E : Throwable> Resource<T, E>.returnIfFailure(*//*block: (Resource.Failure<E>) -> Nothing*//*): T =
    when (this) {
        is Resource.Success -> data
        is Resource.Failure -> Nothing(Resource.Failure(this.reason))
    }*/

inline fun <T : Any, E : Exception> Resource<T, E>.onFailure(block: (Resource.Failure<E>) -> Nothing): T =
    when (this) {
        is Resource.Success -> data
        is Resource.Failure -> block(this)
    }

inline fun <T : Any, E : Exception> Resource<T, E>.onSuccess(block: (Resource.Success<T>) -> Nothing): E =
    when (this) {
        is Resource.Success -> block(this)
        is Resource.Failure -> reason
    }

inline fun <T : Any, E : Exception> Resource<T, E>.fold(
    onSuccess: (Resource.Success<T>) -> Unit,
    onFailure: (Resource.Failure<E>) -> Unit
) {
    when (this) {
        is Resource.Success -> onSuccess(this)
        is Resource.Failure -> onFailure(this)
    }
}





























