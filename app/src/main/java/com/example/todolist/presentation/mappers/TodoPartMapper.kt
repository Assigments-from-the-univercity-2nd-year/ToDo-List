package com.example.todolist.presentation.mappers

import com.example.todolist.data.parts.partsLocalDataSource.entities.TodoPartDbModel

class TodoPartMapper {
    companion object {
        fun mapToDomainModel(todoPartData: TodoPartDbModel): TodoPartDbModel =
            TodoPartDbModel(
                todoPartData.content,
                todoPartData.position,
                todoPartData.parentId,
                todoPartData.isCompleted,
                todoPartData.id
            )

        fun mapToDataModel(todoPart: TodoPartDbModel): TodoPartDbModel =
            TodoPartDbModel(
                todoPart.content,
                todoPart.position,
                todoPart.parentId,
                todoPart.isCompleted,
                todoPart.id
            )
    }
}