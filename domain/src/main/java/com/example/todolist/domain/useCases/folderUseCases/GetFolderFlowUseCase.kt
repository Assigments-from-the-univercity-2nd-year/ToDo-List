package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.repositories.ComponentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFolderFlowUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository
) {

    operator fun invoke(folderId: Long): Flow<Folder> {
        return componentsRepository.getFolderFlow(folderId)
    }

}
