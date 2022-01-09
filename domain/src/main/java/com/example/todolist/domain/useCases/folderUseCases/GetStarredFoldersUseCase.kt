package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class GetStarredFoldersUseCase constructor(
    private val componentsRepository: ComponentsRepository,
) {

    operator fun invoke(): Flow<Resource<List<Folder>, RepositoryExceptions>> =
        componentsRepository.getPinnedFolders()

}