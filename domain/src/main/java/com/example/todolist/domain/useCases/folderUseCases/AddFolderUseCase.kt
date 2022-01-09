package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.FolderCreatingDTO
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.util.Resource
import com.example.todolist.domain.util.onFailure
import javax.inject.Inject

class AddFolderUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository,
) {

    suspend operator fun invoke(folderCreatingDTO: FolderCreatingDTO): Resource<Long, AddFolderUseCaseException> {
        // checking if user input valid or no. If no - return error
        isUserInputCorrect(folderCreatingDTO).onFailure { return it }

        val insertedUserId: Long = componentsRepository.addFolder(folderCreatingDTO)
            .onFailure { return Resource.Failure(
                AddFolderUseCaseException.CantFindInsertedUserInDatabase(it.reason)
            ) }
        val parentFolder: Folder = componentsRepository.getParentFolderOfComponent(insertedUserId)
            .onFailure { return  Resource.Failure(
                AddFolderUseCaseException.CantFindParentFolderInDatabase(it.reason)
            ) }
        componentsRepository.updateFolder(parentFolder.copy(modifiedDate = System.currentTimeMillis()))
            .onFailure { return Resource.Failure(
                AddFolderUseCaseException.CantUpdateParentFolderInDatabase(it.reason)
            ) }

        return Resource.Success(insertedUserId)
    }

    private fun isUserInputCorrect(folderCreatingDTO: FolderCreatingDTO):
            Resource<Unit, AddFolderUseCaseException> {
        return when {
            folderCreatingDTO.title.isBlank() -> {
                Resource.Failure(reason = AddFolderUseCaseException.BlankNameError)
            }
            else -> {
                Resource.Success(data = Unit)
            }
        }
    }

    sealed class AddFolderUseCaseException : Throwable() {
        object BlankNameError : AddFolderUseCaseException()
        data class CantFindInsertedUserInDatabase(override val cause: Throwable) : AddFolderUseCaseException()
        data class CantFindParentFolderInDatabase(override val cause: Throwable) : AddFolderUseCaseException()
        data class CantUpdateParentFolderInDatabase(override val cause: Throwable) : AddFolderUseCaseException()
    }

}