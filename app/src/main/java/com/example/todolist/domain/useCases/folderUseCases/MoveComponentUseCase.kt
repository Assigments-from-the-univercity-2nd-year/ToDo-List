package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Component
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.util.Resource

class MoveComponentUseCase {

    suspend operator fun invoke(component: Component, folderDestination: Folder): Resource<Unit> {
        component.folderId = folderDestination.id
        return component.update()
    }

}