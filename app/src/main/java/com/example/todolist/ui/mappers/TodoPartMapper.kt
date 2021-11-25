package com.example.todolist.ui.mappers

import com.example.todolist.data.entities.TodoPartData
import com.example.todolist.ui.entities.TodoPart

class TodoPartMapper {
    companion object {
        fun mapToDomainModel(todoPartData: TodoPartData): TodoPart =
            TodoPart(
                todoPartData.content.split("\n"),
                todoPartData.position,
                todoPartData.parentId,
                todoPartData.id
            )
    }
}