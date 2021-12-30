package com.example.todolist.domain.models.parts

abstract class Part(
    val position: Int,
    val parentId: Long,
    val id: Long
)