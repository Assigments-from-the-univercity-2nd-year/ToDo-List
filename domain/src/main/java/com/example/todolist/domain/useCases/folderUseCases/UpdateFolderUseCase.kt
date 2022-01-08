package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.util.Resource

class UpdateFolderUseCase constructor(
    private val componentsRepository: ComponentsRepository,
) {

    suspend operator fun invoke(folder: Folder) {
        require(folder.title.isNotBlank())
        require(folder.modifiedDate >= folder.createdDate)
        require(folder.createdDate > 0)

        componentsRepository.updateFolder(folder)
    }

    sealed class UpdateFolderUseCaseExceptions : Exception() {
        object BlankNameException : UpdateFolderUseCaseExceptions()
    }

}