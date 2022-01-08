package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.FolderCreatingDTO
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource
import javax.inject.Inject

class AddFolderUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository,
) {

    suspend operator fun invoke(folderCreatingDTO: FolderCreatingDTO)
    : Resource<Long, AddFolderUseCaseErrors, RepositoryExceptions> {
        return when(val res = isUserInputCorrect(folderCreatingDTO)) {
            is Resource.Success -> {
                addValidUser(folderCreatingDTO = folderCreatingDTO)
            }
            is Resource.Error -> {
                Resource.Error(error = res.error)
            }
            is Resource.Exception -> {
                Resource.Exception(exception = res.exception)
            }
        }
    }

    private fun isUserInputCorrect(folderCreatingDTO: FolderCreatingDTO)
    : Resource<Unit, AddFolderUseCaseErrors, RepositoryExceptions> {
        return when {
            folderCreatingDTO.title.isBlank() -> {
                Resource.Error(error = AddFolderUseCaseErrors.BlankNameError)
            }
            else -> {
                Resource.Success(data = Unit)
            }
        }
    }

    private suspend fun addValidUser(folderCreatingDTO: FolderCreatingDTO)
    : Resource<Long, AddFolderUseCaseErrors, RepositoryExceptions> {
        val addingResult = componentsRepository.addFolder(folderCreatingDTO)
        return when(addingResult) {
            is Resource.Success -> {
                updateParentFolderModificationDate(childFolderId = addingResult.data)
                Resource.Success(data = addingResult.data)
            }
            is Resource.Error -> {
                Resource.Exception(exception = RepositoryExceptions.UnknownException)
            }
            is Resource.Exception -> {
                Resource.Exception(exception = addingResult.exception)
            }
        }
    }

    private suspend fun updateParentFolderModificationDate(childFolderId: Long) {
        val folder = componentsRepository.getParentFolderOfComponent(childFolderId)
        when (folder) {
            is Resource.Success -> {
                val updatingResult = componentsRepository.updateFolder()
            }
        }

    }

    sealed class AddFolderUseCaseErrors {
        object BlankNameError : AddFolderUseCaseErrors()
    }

}