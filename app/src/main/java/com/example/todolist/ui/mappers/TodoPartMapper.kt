package com.example.todolist.ui.mappers

class TodoPartMapper {
    companion object {
        fun mapToDomainModel(todoPartData: com.example.todolist.data.local.partsDataSource.entities.TodoPart): TodoPart =
            TodoPart(
                todoPartData.content,
                todoPartData.position,
                todoPartData.parentId,
                todoPartData.isCompleted,
                todoPartData.id
            )

        fun mapToDataModel(todoPart: TodoPart): com.example.todolist.data.local.partsDataSource.entities.TodoPart =
            com.example.todolist.data.local.partsDataSource.entities.TodoPart(
                todoPart.content,
                todoPart.position,
                todoPart.parentId,
                todoPart.isCompleted,
                todoPart.id
            )
    }
}