package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.util.Resource
import javax.inject.Inject

class DeleteCompletedTasksOfFolderUseCase @Inject constructor() {

    suspend operator fun invoke(folder: Folder): Resource<Unit, Folder.FolderExceptions> {
        TODO("get root folder")
        //return folder.deleteCompletedTasks()
    }


}