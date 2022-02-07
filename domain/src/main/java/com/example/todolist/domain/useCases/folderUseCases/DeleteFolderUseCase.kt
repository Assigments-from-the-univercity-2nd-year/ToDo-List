package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.useCases.tasksUseCases.DeleteTaskUseCase
import javax.inject.Inject

class DeleteFolderUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository
) {

    suspend operator fun invoke(folderId: Long) {
        val folder = componentsRepository.getFolder(folderId)
        folder.subComponents.forEach {
            when (it) {
                is Task -> componentsRepository.deleteTask(it.id)
                is Folder -> componentsRepository.deleteFolder(it.id)
                else -> throw IllegalArgumentException()
            }
        }
        componentsRepository.deleteFolder(folder.id)
    }

}
