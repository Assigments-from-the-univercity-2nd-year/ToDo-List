package com.example.todolist.ui.mappers

import com.example.todolist.data.parts.partsLocalDataSource.entities.TodoPartModel

class TodoPartMapper {
    companion object {
        fun mapToDomainModel(todoPartData: TodoPartModel): TodoPartModel =
            TodoPartModel(
                todoPartData.content,
                todoPartData.position,
                todoPartData.parentId,
                todoPartData.isCompleted,
                todoPartData.id
            )

        fun mapToDataModel(todoPart: TodoPartModel): TodoPartModel =
            TodoPartModel(
                todoPart.content,
                todoPart.position,
                todoPart.parentId,
                todoPart.isCompleted,
                todoPart.id
            )
    }
}