package com.example.todolist.ui.mappers

class TextPartMapper {

    companion object {

        fun mapToDomainModel(textPartData: com.example.todolist.data.local.partsDataSource.entities.TextPart): TextPart =
            TextPart(
                textPartData.content,
                textPartData.position,
                textPartData.parentId,
                textPartData.id
            )

        fun mapToDataModel(textPart: TextPart): com.example.todolist.data.local.partsDataSource.entities.TextPart =
            com.example.todolist.data.local.partsDataSource.entities.TextPart(
                textPart.content,
                textPart.position,
                textPart.parentId,
                textPart.id
            )

    }

}