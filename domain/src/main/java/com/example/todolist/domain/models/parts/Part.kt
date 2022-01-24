package com.example.todolist.domain.models.parts

import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.domain.util.Resource

abstract class Part(
    open val content: Any,
    open val position: Int,
    open val parentId: Long,
    open val id: Long
) {

    abstract suspend fun delete(
        partsRepository: PartsRepository
    ): Resource<Unit, Throwable>

    abstract suspend fun update(
        partsRepository: PartsRepository
    ): Resource<Unit, Throwable>

}