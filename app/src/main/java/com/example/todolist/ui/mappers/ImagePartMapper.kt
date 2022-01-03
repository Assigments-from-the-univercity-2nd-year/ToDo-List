package com.example.todolist.ui.mappers

import android.graphics.Bitmap
import com.example.todolist.data.parts.partsLocalDataSource.entities.ImagePartDbModel

class ImagePartMapper {

    companion object {
        fun mapToDomainModel(imagePartData: ImagePartDbModel, content: Bitmap): ImagePartDbModel =
            ImagePartDbModel(
                content,
                imagePartData.position,
                imagePartData.parentId,
                imagePartData.id
            )

        fun mapToDataModel(imagePart: ImagePartDbModel): ImagePartDbModel =
            ImagePartDbModel(
                imagePart.position,
                imagePart.parentId,
                imagePart.id
            )
    }
}