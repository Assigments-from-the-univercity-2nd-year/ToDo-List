package com.example.todolist.domain

import com.example.todolist.domain.models.components.Component
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.Task
import kotlinx.coroutines.flow.Flow

class FolderInteractor {

    fun getRootFolderUseCase(): Folder {
        TODO()
    }

    fun isRootFolderUseCase(folder: Folder): Boolean {
        TODO()
    }

    fun addFolderUseCase(folder: Folder) {
        TODO()
    }

    fun getFolderUseCase(folderId: Long): Folder {
        TODO()
    }

    fun updateFolderUseCase(folder: Folder) {
        TODO()
    }

    fun deleteFolderUseCase(folder: Folder) {
        TODO()
    }

    fun deleteCompletedTasksOfFolderUseCase(folder: Folder) {
        TODO()
    }

    fun getComponentsOfFolderUseCase(folder: Folder): Flow<List<Component>> {
        TODO()
    }

    fun getStarredFoldersUseCase(): Flow<List<Folder>> {
        TODO()
    }

    fun moveTaskUseCase(task: Task, folderDestination: Folder) {
        TODO()
    }

}