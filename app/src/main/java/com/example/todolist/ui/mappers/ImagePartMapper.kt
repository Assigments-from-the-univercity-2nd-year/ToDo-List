package com.example.todolist.ui.mappers

import com.example.todolist.data.entities.ImagePartData
import com.example.todolist.ui.entities.ImagePart

class ImagePartMapper {

    companion object {
        fun mapToDomainModel(imagePartData: ImagePartData): ImagePart =
            ImagePart(
                imagePartData.content,
                imagePartData.position,
                imagePartData.parentId,
                imagePartData.id
            )

        fun mapToDataModel(imagePart: ImagePart): ImagePartData =
            ImagePartData(
                imagePart.content,
                imagePart.position,
                imagePart.parentId,
                imagePart.id
            )
    }
}