package com.example.todolist.domain.models.parts

import com.example.todolist.domain.models.components.Component

class TextPart(
    val content: String,
    position: Int,
    parentId: Long,
    id: Long
) : Part(position, parentId, id)