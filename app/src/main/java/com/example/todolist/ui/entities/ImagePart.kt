package com.example.todolist.ui.entities

import android.graphics.Bitmap

class ImagePart(
    val content: Bitmap,
    override val position: Int,
    val parentId: Long,
    val id: Long = 0
) : BasePart()