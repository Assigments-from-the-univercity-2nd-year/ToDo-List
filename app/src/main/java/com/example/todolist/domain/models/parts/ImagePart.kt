package com.example.todolist.domain.models.parts

import android.graphics.Bitmap

data class ImagePart(
    override val content: Bitmap,
    override val position: Int,
    override val parentId: Long,
    override val id: Long
) : Part(content, position, parentId, id)