package com.example.todolist.domain.models.components

import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.util.Resource

abstract class Component(
    open var title: String,
    open var folderId: Long,
    open val createdDate: Long,
    open var modifiedDate: Long,
    open val id: Long
) {

    abstract suspend fun delete(
        componentsRepository: ComponentsRepository
    ): Resource<Unit, Throwable>

    abstract suspend fun update(
        componentsRepository: ComponentsRepository
    ): Resource<Unit, Throwable>

}
