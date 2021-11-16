package com.example.todolist.ui.tasks

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.todolist.data.*
import com.example.todolist.ui.ADD_FOLDER_RESULT_OK
import com.example.todolist.ui.ADD_TASK_RESULT_OK
import com.example.todolist.ui.EDIT_FOLDER_RESULT_OK
import com.example.todolist.ui.EDIT_TASK_RESULT_OK
import com.example.todolist.util.exhaustive
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TasksViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    private val folderDao: FolderDao,
    private val preferencesManager: PreferencesManager,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    val searchQuery = state.getLiveData("searchQuery", "")
    val currentFolder = state.getLiveData<Folder>("currentFolder")
    var onAddButtonClicked = state.getLiveData("onAddButtonClicked", false)
    val preferencesFlow = preferencesManager.preferencesFlow
    private val tasksEventChannel = Channel<TasksEvent>()
    val tasksEvent = tasksEventChannel.receiveAsFlow()
    init {
        if (currentFolder.value == null) {
            viewModelScope.launch {
                currentFolder.postValue(folderDao.getRootFolder())
            }
        }
    }

    private val taskFlow = combine(
        searchQuery.asFlow(),
        preferencesFlow,
        currentFolder.asFlow()
    ) { searchQuery, preferencesFlow, currentFolder ->
        Triple(searchQuery, preferencesFlow, currentFolder)
    }.flatMapLatest { (searchQuery, preferences, currentFolder) ->
        combine(
            taskDao.getTasksOfFolder(searchQuery, preferences.hideCompleted, currentFolder.id),
            folderDao.getFoldersOfFolder(currentFolder.id, searchQuery).onEach {
                for (folder in it) {
                    folder.setNumberOfSubComponents(folderDao, taskDao)
                }
            }
        ) { tasks, folders ->
            Pair(tasks, folders)
        }.flatMapLatest { (tasks, folders) ->
            when(preferences.sortOrder) {
                SortOrder.BY_DATE -> {
                    flowOf(tasks.plus<Component>(folders)
                    .sortedByDescending { it.modifiedDate }
                    .sortedWith(CompareComponents))
                }
                SortOrder.BY_NAME ->flowOf(tasks.plus<Component>(folders)
                    .sortedBy { it.title }
                    .sortedWith(CompareComponents))
            }

        }
    }

    val tasks = taskFlow.asLiveData()

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onHideCompletedSelected(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }

    fun onTaskSelected(task: Task) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToEditTaskScreen(task))
    }

    fun onTaskCheckChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        taskDao.updateTask(task.copy(isCompleted = isChecked))
    }

    fun onSubFolderSelected(folder: Folder) = viewModelScope.launch {
        currentFolder.postValue(folderDao.getFolder(folder.id))
        // TODO: change search query and collapse searcing
    }

    fun onHomeButtonSelected() = viewModelScope.launch {
        currentFolder.postValue(folderDao.getFolder(currentFolder.value?.folderId ?: 1L))
    }

    fun onTaskSwiped(task: Task) = viewModelScope.launch {
        taskDao.deleteTask(task)
        val parentFolder = folderDao.getFolder(task.folderId)
        parentFolder.updateDate(folderDao)
        tasksEventChannel.send(TasksEvent.MessageEvent.ShowUndoDeleteTaskMessage(task, parentFolder))
    }

    fun onFolderSwiped(folder: Folder, position: Int) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToDeleteFolderScreen(folder))
        tasksEventChannel.send(TasksEvent.NotifyAdapterItemChanged(position))
    }

    fun onComponentSwiped(component: Component, position: Int) {
        when(component) {
            is Folder -> onFolderSwiped(component, position)
            is Task -> onTaskSwiped(component)
        }.exhaustive
    }

    fun onUndoDeleteClicked(task: Task, parentFolder: Folder) = viewModelScope.launch {
        taskDao.insertTask(task)
        folderDao.updateFolder(parentFolder)
    }

    fun onAddNewTaskClicked() = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToAddTaskScreen)
    }

    fun onAddNewFolderClicked() = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToAddFolderScreen)
    }

    fun onAddButtonClicked() = viewModelScope.launch {
        onAddButtonClicked.value = !(onAddButtonClicked.value ?: false)
    }

    fun onAddEditResult(result: Int) {
        when (result) {
            ADD_TASK_RESULT_OK -> showTaskSavedConfirmationMessage("Task added")
            EDIT_TASK_RESULT_OK ->showTaskSavedConfirmationMessage("Task updated")
        }
    }

    fun onAddEditFolderResult(result: Int) = viewModelScope.launch {
        when (result) {
            ADD_FOLDER_RESULT_OK -> showTaskSavedConfirmationMessage("Folder added")
            EDIT_FOLDER_RESULT_OK -> {
                showTaskSavedConfirmationMessage("Folder updated")
                currentFolder.postValue(folderDao.getFolder(currentFolder.value!!.id))
            }
        }
    }

    fun onQuickFolderChangeResult(result: Folder?) {
        if (result != null) {
            currentFolder.value = result
        }
    }

    private fun showTaskSavedConfirmationMessage(msg: String) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.MessageEvent.ShowTaskSavedConfirmationMessage(msg))
    }

    fun onDeleteAllCompletedClicked() = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToDeleteAllCompletedScreen)
    }

    fun onQuickFolderChangeClicked() = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToQuickFolderChange(
            folderDao.getPinnedFolders().onEach {
                it.setNumberOfSubComponents(folderDao, taskDao)
            }
        ))
    }

    fun onEditFolderClicked() = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToEditFolderScreen(
            folderDao.getFolder(currentFolder.value!!.folderId ?: 1L)
        ))
    }

    fun taskMovedToFolder(component: Component?, folder: Folder?) = viewModelScope.launch {
        if (folder != null) {
            when(component) {
                is Task -> {
                    taskDao.updateTask(component.copy(
                        folderId = folder.id,
                        modifiedDate = System.currentTimeMillis()
                    ))
                    folder.updateDate(folderDao)
                }
                is Folder -> {
                    folderDao.updateFolder(
                        component.copy(
                            folderId = folder.id,
                            modifiedDate = System.currentTimeMillis()
                        )
                    )
                    folder.updateDate(folderDao)
                }
                null -> {
                    // do nothing
                }
            }.exhaustive
        }
    }

    sealed class TasksEvent {
        sealed class NavigationEvent {
            object NavigateToAddTaskScreen : TasksEvent()
            data class NavigateToEditTaskScreen(val task: Task) : TasksEvent()
            object NavigateToAddFolderScreen : TasksEvent()
            data class NavigateToEditFolderScreen(val parentFolder: Folder) : TasksEvent()
            object NavigateToDeleteAllCompletedScreen : TasksEvent()
            data class NavigateToDeleteFolderScreen(val folder: Folder) : TasksEvent()
            data class NavigateToQuickFolderChange(val pinnedFolders: List<Folder>) : TasksEvent()
        }
        sealed class MessageEvent {
            data class ShowUndoDeleteTaskMessage(val task: Task, val parentFolder: Folder) : TasksEvent()
            data class ShowTaskSavedConfirmationMessage(val msg: String) : TasksEvent()
            data class ShowFolderSavedConfirmationMessage(val msg: String) : TasksEvent()
        }
        data class NotifyAdapterItemChanged(val position: Int) : TasksEvent()
    }
}
