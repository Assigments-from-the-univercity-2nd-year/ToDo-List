package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.userPreferences.SortOrder
import com.example.todolist.domain.models.components.Component
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.models.userPreferences.FilterPreferences
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.repositories.UserPreferencesRepository
import com.example.todolist.util.Resource
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetComponentsOfFolderUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {

    operator fun invoke(folder: Folder): Resource<Flow<List<Component>>> {

        val tasksResourceFlow = componentsRepository.getSubFoldersOfFolder(folder)
        val foldersResourceFlow = componentsRepository.getTasksOfFolder(folder)
        val preferencesResourceFlow = userPreferencesRepository.getFilterPreferences()

        return when {
            tasksResourceFlow is Resource.Error -> Resource.Error(tasksResourceFlow.exception)
            foldersResourceFlow is Resource.Error -> Resource.Error(foldersResourceFlow.exception)
            preferencesResourceFlow is Resource.Error -> Resource.Error(preferencesResourceFlow.exception)

            else -> onSuccess(
                tasksResourceFlow as Resource.Success,
                foldersResourceFlow as Resource.Success,
                preferencesResourceFlow as Resource.Success
            )
        }
    }

    private fun onSuccess(
        tasksSuccessFlow: Resource.Success<Flow<List<Folder>>>,
        foldersSuccessFlow: Resource.Success<Flow<List<Task>>>,
        preferencesSuccessFlow: Resource.Success<Flow<FilterPreferences>>
    ) : Resource.Success<Flow<List<Component>>> =
        Resource.Success(
            combine(
                tasksSuccessFlow.data,
                foldersSuccessFlow.data,
                preferencesSuccessFlow.data
            ) { tasks, folders, preferences ->
                folders.plus(tasks)
                    .also { filtrateComponents(it, preferences.hideCompleted) }
                    .also { sortComponents(it, preferences.sortOrder) }
            }
        )

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
}