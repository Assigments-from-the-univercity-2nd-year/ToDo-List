package com.example.todolist.presentation.tasks

import androidx.hilt.Assisted
import androidx.lifecycle.*
import com.example.todolist.data.userPreferences.userPreferencesLocalDataSource.userPreferencesLocalDataStore.UserPreferencesDataStore
import com.example.todolist.domain.models.userPreferences.SortOrder
import com.example.todolist.domain.useCases.folderUseCases.GetComponentsOfFolderUseCase
import com.example.todolist.domain.useCases.folderUseCases.GetRootFolderUseCase
import com.example.todolist.domain.useCases.folderUseCases.UpdateFolderUseCase
import com.example.todolist.domain.useCases.userPreferencesUseCases.UpdateHideCompletedUseCase
import com.example.todolist.domain.useCases.userPreferencesUseCases.UpdateSortOrderUseCase
import com.example.todolist.domain.util.Resource
import com.example.todolist.domain.util.onFailure
import com.example.todolist.presentation.entities.components.ComponentUiState
import com.example.todolist.presentation.entities.components.FolderUiState
import com.example.todolist.presentation.entities.components.TaskUiState
import com.example.todolist.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val preferencesManager: UserPreferencesDataStore,
    @Assisted private val state: SavedStateHandle,

    private val getRootFolderUseCase: GetRootFolderUseCase,
    private val getComponentsOfFolderUseCase: GetComponentsOfFolderUseCase,

    //private val addFolderUseCase: AddFolderUseCase,
    private val updateSortOrderUseCase: UpdateSortOrderUseCase,
    private val updateHideCompletedUseCase: UpdateHideCompletedUseCase,

    private val updateFolderUseCase: UpdateFolderUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState = _uiState.asLiveData()

    private val tasksEventChannel = Channel<TasksEvent>()
    val tasksEvent = tasksEventChannel.receiveAsFlow()

    //    val searchQuery = state.getLiveData("searchQuery", "")

    init {
        //todo: fetchTaskData()
        _uiState.value = TasksUiState(
            isLoading = false,
            folderData = initRootFolderTemp(),
            components = initComponentsTemp(),
        )
    }

    private fun initRootFolderTemp(): FolderUiState {
        return FolderUiState(
            title = "Root folder",
            folderId = 1,
            isPinned = true,
            id = 1,
            numberOfSubComponents = "8",
        )
    }

    private fun initComponentsTemp(): List<ComponentUiState> {
        return listOf(
            TaskUiState(title = "Note #1", isImportant = true),
            TaskUiState(title = "Note #2", isImportant = true),
            FolderUiState(title = "Folder #1"),
            TaskUiState(title = "Note #3"),
            TaskUiState(title = "Note #4"),
            TaskUiState(title = "Note #5"),
            FolderUiState(title = "Folder #2"),
            TaskUiState(title = "Note #6"),
        )
    }

    private fun fetchTaskData() = viewModelScope.launch {
        val rootFolder = getRootFolderUseCase()
            .onFailure { TODO() }
        val componentsList = getComponentsOfFolderUseCase(rootFolder)
        componentsList.collect {
            when (it) {
                is Resource.Failure -> TODO()
                is Resource.Success -> {

                }
            }.exhaustive
        }
        //TODO: fetch data from useCases to _uiState
    }

    fun isCurrentFolderRoot(): Boolean {
        val folderData = _uiState.value.folderData
        return if (folderData != null) {
            folderData.id == folderData.folderId
        } else {
            false
        }
    }

    fun getTitleName(rootFolderTitle: String): String {
        return if (isCurrentFolderRoot()) {
            rootFolderTitle
        } else {
            _uiState.value.folderData?.title ?: TODO()
        }
    }

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        updateSortOrderUseCase(sortOrder)
    }

    fun onHideCompletedSelected(hideCompleted: Boolean) = viewModelScope.launch {
        updateHideCompletedUseCase(hideCompleted)
    }

    fun onTaskSelected(task: TaskUiState) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToEditTaskScreen(task))
    }

    fun onTaskCheckChanged(task: TaskUiState, isChecked: Boolean) = viewModelScope.launch {
        //taskDao.updateTask(task.copy(isCompleted = isChecked))
        // TODO: implement
    }

    fun onSubFolderSelected(folder: FolderUiState) = viewModelScope.launch {
        //currentFolder.postValue(folderDao.getFolder(folder.id))
        // TODO: change search query and collapse searcing
    }

    fun onHomeButtonSelected() = viewModelScope.launch {
        //TODO: navigate to the parent folder of the current folder
        //_uiState.value = _uiState.value
        //currentFolder.postValue(folderDao.getFolder(currentFolder.value?.folderId ?: 1L))
    }

    /*fun onTaskSwiped(task: Task) = viewModelScope.launch {
        taskDao.deleteTask(task)
        val parentFolder = folderDao.getFolder(task.folderId)
        parentFolder.updateDate(folderDao)
        tasksEventChannel.send(TasksEvent.MessageEvent.ShowUndoDeleteTaskMessage(task, parentFolder))
    }*/

    /*fun onFolderSwiped(folder: Folder, position: Int) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToDeleteFolderScreen(folder))
        tasksEventChannel.send(TasksEvent.NotifyAdapterItemChanged(position))
    }*/

    fun onComponentSwiped(position: Int) {
        val component = _uiState.value.components[position]
        //TODO()
        /*when(component) {
            is Folder -> onFolderSwiped(component, position)
            is Task -> onTaskSwiped(component)
        }.exhaustive*/
    }

    fun onUndoDeleteClicked(task: TaskUiState, parentFolder: FolderUiState) = viewModelScope.launch {
        //TODO
        //taskDao.insertTask(task)
        //folderDao.updateFolder(parentFolder)
    }

    fun onAddNewTaskClicked() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(fabAnimation = TasksUiState.FABAnimation.DO_NOTHING)
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToAddTaskScreen)
    }

    fun onAddNewFolderClicked() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(fabAnimation = TasksUiState.FABAnimation.HIDE_FABS)
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToAddFolderScreen)
    }

    fun onAddButtonClicked() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            fabAnimation = when (_uiState.value.fabAnimation) {
                TasksUiState.FABAnimation.SHOW_FABS -> TasksUiState.FABAnimation.HIDE_FABS
                else -> TasksUiState.FABAnimation.SHOW_FABS
            }
        )
    }

    /*fun onAddEditResult(result: Int) {
        when (result) {
            ADD_TASK_RESULT_OK -> showTaskSavedConfirmationMessage("Task added")
            EDIT_TASK_RESULT_OK -> showTaskSavedConfirmationMessage("Task updated")
            EDIT_TASK_RESULT_NOTHING_CHANGED -> showTaskSavedConfirmationMessage("Task data didn't change")
        }
    }*/

    /*fun onAddEditFolderResult(result: Int) = viewModelScope.launch {
        when (result) {
            ADD_FOLDER_RESULT_OK -> showTaskSavedConfirmationMessage("Folder added")
            EDIT_FOLDER_RESULT_OK -> {
                showTaskSavedConfirmationMessage("Folder updated")
                currentFolder.postValue(folderDao.getFolder(currentFolder.value!!.id))
            }
        }
    }*/

    /*fun onQuickFolderChangeResult(result: Folder?) {
        if (result != null) {
            currentFolder.value = result
        }
    }*/

    /*private fun showTaskSavedConfirmationMessage(msg: String) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.MessageEvent.ShowTaskSavedConfirmationMessage(msg))
    }*/

    fun onDeleteAllCompletedClicked() = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToDeleteAllCompletedScreen)
    }

    fun onQuickFolderChangeClicked() = viewModelScope.launch {
        //TODO: choose whether list of starred folders should be fetched here or in fragment
        /*tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToQuickFolderChange(

            /*folderDao.getPinnedFolders().onEach {
                it.setNumberOfSubComponents(folderDao, taskDao)
            }*/
        ))*/
    }

    fun onEditFolderClicked() = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToEditFolderScreen(
            _uiState.value.folderData ?: TODO()
        ))
    }

    /*fun taskMovedToFolder(component: Component?, folder: Folder?) = viewModelScope.launch {
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
    }*/

    sealed class TasksEvent {

        sealed class NavigationEvent {
            object NavigateToAddTaskScreen : TasksEvent()
            object NavigateToAddFolderScreen : TasksEvent()

            data class NavigateToEditTaskScreen(val task: TaskUiState) : TasksEvent()
            data class NavigateToEditFolderScreen(val parentFolder: FolderUiState) : TasksEvent()

            object NavigateToDeleteAllCompletedScreen : TasksEvent()
            data class NavigateToDeleteFolderScreen(val folder: FolderUiState) : TasksEvent()

            data class NavigateToQuickFolderChange(val pinnedFolders: List<FolderUiState>) : TasksEvent()
        }

        sealed class MessageEvent {
            data class ShowUndoDeleteTaskMessage(val task: TaskUiState, val parentFolder: FolderUiState) : TasksEvent()

            data class ShowTaskSavedConfirmationMessage(val msg: String) : TasksEvent()
            data class ShowFolderSavedConfirmationMessage(val msg: String) : TasksEvent()
        }

        //data class NotifyAdapterItemChanged(val position: Int) : TasksEvent()

    }

}
