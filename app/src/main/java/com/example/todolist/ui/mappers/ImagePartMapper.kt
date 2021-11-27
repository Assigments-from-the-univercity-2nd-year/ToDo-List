package com.example.todolist.ui.mappers

import android.graphics.Bitmap
import com.example.todolist.data.entities.ImagePartData
import com.example.todolist.ui.entities.ImagePart

class ImagePartMapper {

    companion object {
        fun mapToDomainModel(imagePartData: ImagePartData, content: Bitmap): ImagePart =
            ImagePart(
                content,
                imagePartData.position,
                imagePartData.parentId,
                imagePartData.id
            )

        fun mapToDataModel(imagePart: ImagePart): ImagePartData =
            ImagePartData(
                imagePart.position,
                imagePart.parentId,
                imagePart.id
            )
    }
}