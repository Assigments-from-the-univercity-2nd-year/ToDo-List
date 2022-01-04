package com.example.todolist.domain.models.parts

data class ImagePart(
    override val content: ByteArray,
    override val position: Int,
    override val parentId: Long,
    override val id: Long
) : Part(content, position, parentId, id)