package com.example.todolist.domain.models.components

import com.example.todolist.util.Resource

abstract class Component(
    open var title: String,
    open var folderId: Long,
    open val createdDate: Long,
    open var modifiedDate: Long,
    open val id: Long
) {

    abstract suspend fun delete(): Resource<Unit>

    abstract suspend fun update(): Resource<Unit>

}
