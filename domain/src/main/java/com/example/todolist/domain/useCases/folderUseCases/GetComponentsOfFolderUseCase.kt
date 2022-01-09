package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.userPreferences.SortOrder
import com.example.todolist.domain.models.components.Component
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.repositories.UserPreferencesRepository
import com.example.todolist.domain.util.Resource
import com.example.todolist.domain.util.onFailure
import kotlinx.coroutines.flow.*

class GetComponentsOfFolderUseCase constructor(
    private val componentsRepository: ComponentsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {

    operator fun invoke(folder: Folder): Flow<Resource<List<Component>, GetComponentsOfFolderUseCaseException>> {
        return combine(
            componentsRepository.getSubFoldersOfFolder(folder),
            componentsRepository.getTasksOfFolder(folder),
            userPreferencesRepository.getFilterPreferences()
        ) { foldersRes,
            tasksRes,
            preferencesRes ->

            val folders = foldersRes.onFailure {
                return@combine Resource.Failure(GetComponentsOfFolderUseCaseException.ProblemWithTasksFlow(it.reason))
            }
            val tasks = tasksRes.onFailure {
                return@combine Resource.Failure(GetComponentsOfFolderUseCaseException.ProblemWithFolderFlow(it.reason))
            }
            val preferences = preferencesRes.onFailure {
                return@combine Resource.Failure(GetComponentsOfFolderUseCaseException.ProblemWithFilterPreferencesFlow(it.reason))
            }

            Resource.Success(
                folders.plus(tasks)
                    .also { filtrateComponents(it, preferences.hideCompleted) }
                    .also { sortComponents(it, preferences.sortOrder) }
            )
        }
    }

    private fun filtrateComponents(components: List<Component>, hideCompleted: Boolean) =
        components.filter { !(it is Task && it.isCompleted && hideCompleted) }

    private fun sortComponents(components: List<Component>, sortOrder: SortOrder) =
        when (sortOrder) {
            SortOrder.BY_DATE -> {
                components.sortedByDescending { it.modifiedDate }
                    .sortedWith(ComponentsComparator)
            }
            SortOrder.BY_NAME -> {
                components.sortedBy { it.title }
                    .sortedWith(ComponentsComparator)
            }
        }

    private object ComponentsComparator : Comparator<Component> {
        override fun compare(o1: Component?, o2: Component?) = when {
            (o1 as? Task)?.isImportant ?: false -> {
                if ((o2 as? Task)?.isImportant == true) {
                    0
                } else {
                    -1
                }
            }
            (o2 as? Task)?.isImportant ?: false -> {
                1
            }
            else -> 0
        }

    }

    sealed class GetComponentsOfFolderUseCaseException : Throwable() {
        data class ProblemWithTasksFlow(override val cause: Throwable) : GetComponentsOfFolderUseCaseException()
        data class ProblemWithFolderFlow(override val cause: Throwable) : GetComponentsOfFolderUseCaseException()
        data class ProblemWithFilterPreferencesFlow(override val cause: Throwable) : GetComponentsOfFolderUseCaseException()
    }
}