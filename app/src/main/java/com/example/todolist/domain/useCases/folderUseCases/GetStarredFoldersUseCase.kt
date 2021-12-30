package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.repositories.ComponentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStarredFoldersUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository,
) {

    operator fun invoke(): Flow<List<Folder>> =
        componentsRepository.getPinnedFolders()

}