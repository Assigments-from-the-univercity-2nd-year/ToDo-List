package com.example.todolist.ui.mappers

import com.example.todolist.data.entities.TodoPartData
import com.example.todolist.ui.entities.TodoPart

class TodoPartMapper {
    companion object {
        fun mapToDomainModel(todoPartData: TodoPartData): TodoPart =
            TodoPart(
                todoPartData.content,
                todoPartData.position,
                todoPartData.parentId,
                todoPartData.isCompleted,
                todoPartData.id
            )

        fun mapToDataModel(todoPart: TodoPart): TodoPartData =
            TodoPartData(
                todoPart.content,
                todoPart.position,
                todoPart.parentId,
                todoPart.isCompleted,
                todoPart.id
            )
    }
}