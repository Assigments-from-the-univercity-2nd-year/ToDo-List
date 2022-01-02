package com.example.todolist.domain.models.parts

import android.graphics.Bitmap
import com.example.todolist.domain.models.components.Component

class ImagePart(
    val content: Bitmap,
    position: Int,
    parentId: Long,
    id: Long
) : Part(position, parentId, id)