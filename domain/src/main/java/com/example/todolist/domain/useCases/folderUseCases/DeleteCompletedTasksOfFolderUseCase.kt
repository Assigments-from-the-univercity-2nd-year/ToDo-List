package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.repositories.ComponentsRepository
import javax.inject.Inject

class DeleteCompletedTasksOfFolderUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository
) {

    suspend operator fun invoke(folderId: Long) {
        //TODO: implement
        //folder.deleteCompletedTasks(componentsRepository)
    }

}
