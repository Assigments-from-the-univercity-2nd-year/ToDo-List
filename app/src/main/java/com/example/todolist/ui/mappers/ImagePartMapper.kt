package com.example.todolist.ui.mappers

import android.graphics.Bitmap

class ImagePartMapper {

    companion object {
        fun mapToDomainModel(imagePartData: com.example.todolist.data.local.partsDataSource.entities.ImagePart, content: Bitmap): ImagePart =
            ImagePart(
                content,
                imagePartData.position,
                imagePartData.parentId,
                imagePartData.id
            )

        fun mapToDataModel(imagePart: ImagePart): com.example.todolist.data.local.partsDataSource.entities.ImagePart =
            com.example.todolist.data.local.partsDataSource.entities.ImagePart(
                imagePart.position,
                imagePart.parentId,
                imagePart.id
            )
    }
}