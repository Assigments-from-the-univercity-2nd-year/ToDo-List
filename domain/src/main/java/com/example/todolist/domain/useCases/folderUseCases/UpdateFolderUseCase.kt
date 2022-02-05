package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.repositories.ComponentsRepository

class UpdateFolderUseCase constructor(
    private val componentsRepository: ComponentsRepository,
) {

    suspend operator fun invoke(folder: Folder) {
        // maybe move thees checks to folder.
        // Like create a checkInvariant() function
        require(folder.title.isNotBlank())
        require(folder.modifiedDate >= folder.createdDate)
        require(folder.createdDate > 0)

        componentsRepository.updateFolder(folder)
    }

}
