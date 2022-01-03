package com.example.todolist.domain.models.parts

class TextPart(
    override val content: String,
    override val position: Int,
    override val parentId: Long,
    override val id: Long
) : Part(content, position, parentId, id)