package com.example.todolist.domain.models.parts

abstract class Part(
    open val content: Any,
    open val position: Int,
    open val parentId: Long,
    open val id: Long
)