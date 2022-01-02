package com.example.todolist.ui.mappers

import com.example.todolist.data.parts.partsLocalDataSource.entities.TextPartModel

class TextPartMapper {

    companion object {

        fun mapToDomainModel(textPartData: TextPartModel): TextPartModel =
            TextPartModel(
                textPartData.content,
                textPartData.position,
                textPartData.parentId,
                textPartData.id
            )

        fun mapToDataModel(textPart: TextPartModel): TextPartModel =
            TextPartModel(
                textPart.content,
                textPart.position,
                textPart.parentId,
                textPart.id
            )

    }

}