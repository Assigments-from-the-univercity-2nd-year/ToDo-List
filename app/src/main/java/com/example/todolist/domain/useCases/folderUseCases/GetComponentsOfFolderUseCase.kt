package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Component
import com.example.todolist.domain.models.components.Folder
import kotlinx.coroutines.flow.Flow

class GetComponentsOfFolderUseCase {

    operator fun invoke(folder: Folder): Flow<List<Component>> {
        TODO()
    }

}