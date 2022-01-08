package com.example.todolist.presentation.mappers

import com.example.todolist.data.parts.partsLocalDataSource.entities.TextPartDbModel

class TextPartMapper {

    companion object {

        fun mapToDomainModel(textPartData: TextPartDbModel): TextPartDbModel =
            TextPartDbModel(
                textPartData.content,
                textPartData.position,
                textPartData.parentId,
                textPartData.id
            )

        fun mapToDataModel(textPart: TextPartDbModel): TextPartDbModel =
            TextPartDbModel(
                textPart.content,
                textPart.position,
                textPart.parentId,
                textPart.id
            )

    }

}