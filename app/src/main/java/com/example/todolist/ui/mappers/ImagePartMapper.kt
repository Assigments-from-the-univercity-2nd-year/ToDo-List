package com.example.todolist.ui.mappers

import android.graphics.Bitmap
import com.example.todolist.data.parts.partsLocalDataSource.entities.ImagePartModel

class ImagePartMapper {

    companion object {
        fun mapToDomainModel(imagePartData: ImagePartModel, content: Bitmap): ImagePartModel =
            ImagePartModel(
                content,
                imagePartData.position,
                imagePartData.parentId,
                imagePartData.id
            )

        fun mapToDataModel(imagePart: ImagePartModel): ImagePartModel =
            ImagePartModel(
                imagePart.position,
                imagePart.parentId,
                imagePart.id
            )
    }
}