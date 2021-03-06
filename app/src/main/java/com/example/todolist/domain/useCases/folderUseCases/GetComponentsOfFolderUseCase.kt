package com.example.todolist.domain.useCases.folderUseCases

import com.example.todolist.domain.models.SortOrder
import com.example.todolist.domain.models.components.Component
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetComponentsOfFolderUseCase @Inject constructor(
    private val componentsRepository: ComponentsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {

    operator fun invoke(folder: Folder): Flow<List<Component>> = combine(
            componentsRepository.getComponentsOfFolder(folder),
            userPreferencesRepository.getFilterPreferences()
        ) { components, preferences ->
            filtrateComponents(components, preferences.hideCompleted)
                .also { sortComponents(it, preferences.sortOrder) }
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
}