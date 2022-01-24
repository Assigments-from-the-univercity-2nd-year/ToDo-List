package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Component
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.util.Resource

class MoveComponentUseCase {

    suspend operator fun invoke(component: Component, folderDestination: Folder): Resource<Unit, Throwable> {
        component.folderId = folderDestination.id
        TODO()
        //return component.update()
    }

}