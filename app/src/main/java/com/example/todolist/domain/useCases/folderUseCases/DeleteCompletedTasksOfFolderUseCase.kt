package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Folder

class DeleteCompletedTasksOfFolderUseCase {

    operator fun invoke(folder: Folder) {
        folder.deleteCompletedTasks()
    }

}