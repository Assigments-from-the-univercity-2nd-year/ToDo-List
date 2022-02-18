package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.repositories.ComponentsRepository
import javax.inject.Inject

/**
 * This use case is used for deleting completed tasks
 */
class DeleteCompletedTasksOfFolderUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository
) {

    /**
     * delete all completed tasks that are in a folder with id equal to [folderId]
     *
     * @param folderId an id of folder to delete
     */
    suspend operator fun invoke(folderId: Long) {
        val folder = componentsRepository.getFolder(folderId)
        folder.subComponents.forEach {
            when (it) {
                is Task -> if (it.isCompleted) { componentsRepository.deleteTask(it.id) }
                is Folder -> this(it.id)
                else -> throw IllegalArgumentException()
            }
        }
    }

}
