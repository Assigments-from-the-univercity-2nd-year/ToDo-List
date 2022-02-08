package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.util.Resource
import com.example.todolist.domain.util.onFailure
import javax.inject.Inject

class AddFolderUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository,
) {

    suspend operator fun invoke(
        title: String,
        parentFolderId: Long,
        isStarred: Boolean,
    ): Resource<Long, AddFolderUseCaseException> {
        require(parentFolderId > 0)
        isUserInputCorrect(title).onFailure { return it }
        val folder = Folder(
            title = title,
            parentFolderId = parentFolderId,
            isStarred = isStarred,
        )

        val insertedFolderId: Long = componentsRepository.addFolder(folder)
        val parentFolder: Folder = componentsRepository.getFolder(parentFolderId)
        componentsRepository.updateFolder(parentFolder.copy(modifiedDate = System.currentTimeMillis()))

        return Resource.Success(insertedFolderId)
    }

    private fun isUserInputCorrect(title: String): Resource<Unit, AddFolderUseCaseException> {
        return when {
            title.isBlank() -> {
                Resource.Failure(reason = AddFolderUseCaseException.BlankNameError)
            }
            else -> {
                Resource.Success(data = Unit)
            }
        }
    }

    sealed class AddFolderUseCaseException : Exception() {
        object BlankNameError : AddFolderUseCaseException()
    }

}
