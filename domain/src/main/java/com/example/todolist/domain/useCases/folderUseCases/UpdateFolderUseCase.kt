package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.util.Resource
import com.example.todolist.domain.util.onFailure

class UpdateFolderUseCase constructor(
    private val componentsRepository: ComponentsRepository,
) {

    suspend operator fun invoke(
        folderId: Long,
        title: String,
        isStarred: Boolean,
    ): Resource<Unit, UpdateFolderUseCaseException> {
        require(folderId > 0)
        isUserInputCorrect(title).onFailure { return it }

        val folder = componentsRepository.getFolder(folderId) //TODO: if folder doesn't exists
        val parentFolder = componentsRepository.getFolder(folder.parentFolderId)
        componentsRepository.updateFolder(
            folder.copy(title = title, isStarred = isStarred)
        )
        componentsRepository.updateFolder(parentFolder)
        return Resource.Success(Unit)
    }

    private fun isUserInputCorrect(title: String): Resource<Unit, UpdateFolderUseCaseException> {
        return when {
            title.isBlank() -> {
                Resource.Failure(reason = UpdateFolderUseCaseException.BlankNameError)
            }
            else -> {
                Resource.Success(data = Unit)
            }
        }
    }

    sealed class UpdateFolderUseCaseException : Exception() {
        object BlankNameError : UpdateFolderUseCaseException()
    }

}
