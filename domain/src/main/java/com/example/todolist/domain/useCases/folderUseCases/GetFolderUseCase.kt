package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetFolderUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository
) {

    operator fun invoke(folderId: Long): Flow<Resource<Folder, RepositoryExceptions>> {
        //TODO()
        return flowOf()
    }

}
