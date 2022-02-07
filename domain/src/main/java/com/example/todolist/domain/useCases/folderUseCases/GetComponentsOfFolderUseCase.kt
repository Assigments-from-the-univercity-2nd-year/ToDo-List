package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.components.Component
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.models.userPreferences.SortOrder
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetComponentsOfFolderUseCase constructor(
    private val componentsRepository: ComponentsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {

    operator fun invoke(folder: Folder): Flow<List<Component>> {
        return combine(
            componentsRepository.getSubFoldersFlow(folder.id),
            componentsRepository.getSubTasksFlow(folder.id),
            userPreferencesRepository.getFilterPreferences()
        ) { folders, tasks, preferences ->
            folders.plus(tasks)
                .filtrateComponents(preferences.hideCompleted)
                .sortComponents(preferences.sortOrder)
        }
    }

    private fun List<Component>.filtrateComponents(hideCompleted: Boolean) =
        this.filter { !(it is Task && it.isCompleted && hideCompleted) }

    private fun List<Component>.sortComponents(sortOrder: SortOrder) =
        when (sortOrder) {
            SortOrder.BY_DATE -> {
                this.sortedByDescending { it.modifiedDate }
                    .sortedWith(ComponentsComparator)
            }
            SortOrder.BY_NAME -> {
                this.sortedBy { it.title }
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
