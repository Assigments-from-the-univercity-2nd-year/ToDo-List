package com.example.todolist.ui.mappers

import com.example.todolist.data.entities.TextPartData
import com.example.todolist.ui.entities.TextPart

class TextPartMapper {

    companion object {

        fun mapToDomainModel(textPartData: TextPartData): TextPart =
            TextPart(
                textPartData.content,
                textPartData.position,
                textPartData.parentId,
                textPartData.id
            )

        fun matToDataModel(textPart: TextPart): TextPartData =
            TextPartData(
                textPart.content,
                textPart.position,
                textPart.parentId,
                textPart.id
            )

    }

}