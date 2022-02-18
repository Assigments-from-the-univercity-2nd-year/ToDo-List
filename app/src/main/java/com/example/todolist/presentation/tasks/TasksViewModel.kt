package com.example.todolist.presentation.tasks

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.R
import com.example.todolist.domain.models.components.Component
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.models.userPreferences.SortOrder
import com.example.todolist.domain.useCases.folderUseCases.*
import com.example.todolist.domain.useCases.tasksUseCases.AddTaskUseCase
import com.example.todolist.domain.useCases.tasksUseCases.DeleteTaskUseCase
import com.example.todolist.domain.useCases.tasksUseCases.updateTaskCheckUseCase
import com.example.todolist.domain.useCases.userPreferencesUseCases.UpdateHideCompletedUseCase
import com.example.todolist.domain.useCases.userPreferencesUseCases.UpdateSortOrderUseCase
import com.example.todolist.presentation.*
import com.example.todolist.presentation.entities.components.*
import com.example.todolist.presentation.tasks.componentAdapter.ComponentFingerprint
import com.example.todolist.presentation.tasks.componentAdapter.folder.FolderFingerprint
import com.example.todolist.presentation.tasks.componentAdapter.task.TaskFingerprint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getRootFolderUseCase: GetRootFolderUseCase,
    private val getStarredFoldersUseCase: GetStarredFoldersUseCase,
    private val getFolderFlowUseCase: GetFolderFlowUseCase,
    private val getFolderUseCase: GetFolderUseCase,
    private val getComponentsOfFolderUseCase: GetComponentsOfFolderUseCase,

    private val updateSortOrderUseCase: UpdateSortOrderUseCase,
    private val updateHideCompletedUseCase: UpdateHideCompletedUseCase,

    private val updateTaskCheckUseCase: updateTaskCheckUseCase,

    private val deleteTaskUseCase: DeleteTaskUseCase,

    private val addTaskUseCase: AddTaskUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())

    /**
     * the ui state for [TasksFragment]
     */
    val uiState = _uiState.asLiveData()

    private val tasksEventChannel = Channel<TasksEvent>()

    /**
     * the event channel to collect by [TasksFragment]
     */
    val tasksEvent = tasksEventChannel.receiveAsFlow()

    /**
     * a list of Fingerprints with corresponding actions
     */
    val fingerprintsWithAction = initFingerprintsWithAction()

    /**
     * a list of Fingerprints
     */
    val fingerprints = fingerprintsWithAction.map { it.fingerprint }

    //    val searchQuery = state.getLiveData("searchQuery", "")

    init {
        fetchTasksData()
    }

    private fun fetchTasksData() = viewModelScope.launch {
        val rootFolder = getRootFolderUseCase()
        navigateToFolder(rootFolder.id)
    }

    private suspend fun navigateToFolder(folderId: Long) {
        val currentFolderFlow = getFolderFlowUseCase(folderId)
        currentFolderFlow.collect { currentFolder ->
            getComponentsOfFolderUseCase(currentFolder.id).collect {
                _uiState.value = TasksUiState(
                    isLoading = false,
                    fabAnimation = _uiState.value.fabAnimation,
                    folderData = currentFolder.mapToPresentation(),
                    components = it.mapListOfComponentsToPresentation(),
                )
                Log.i("TAG", "navigateToFolder: state updated!")
                Log.i("TAG", "state: ${_uiState.value}")
            }
        }
    }

    private fun List<Component>.mapListOfComponentsToPresentation(): List<ComponentUiState> {
        return this.map {
            when(it) {
                is Task -> it.mapToPresentation()
                is Folder -> it.mapToPresentation()
                else -> throw NoSuchElementException()
            }
        }
    }

    /**
     * Check whether [uiState] points to root folder or not
     *
     * @return true if the folder is root, otherwise false
     */
    fun isCurrentFolderRoot(): Boolean {
        val folderData = _uiState.value.folderData ?: return false
        return folderData.id == folderData.parentFolderId
    }

    /**
     * returns name for title to display in header
     *
     * @param resources variable to have access to android resources
     *
     * @return the title
     */
    fun getTitleName(resources: Resources): String {
        return if (isCurrentFolderRoot()) {
            resources.getString(R.string.taskfragment_all_tasks_title)
        } else {
            _uiState.value.folderData
                ?.title
                ?: resources.getString(R.string.taskfragment_loading_title)
        }
    }

    private fun initFingerprintsWithAction(): List<FingerprintsWithAction> {
        return listOf(
            getFolderFingerprintWithAction(),
            getTaskFingerprintWithAction(),
        )
    }

    private fun getFolderFingerprintWithAction(): FingerprintsWithAction {
        return FingerprintsWithAction(
            FolderFingerprint { folder -> onSubFolderSelected(folder) },
            onSwipe = { position ->
                val folder = _uiState.value.components[position] as FolderUiState
                onFolderSwiped(folder, position)
            }
        )
    }

    private fun getTaskFingerprintWithAction(): FingerprintsWithAction {
        return FingerprintsWithAction(
            TaskFingerprint(
                onTaskClicked = { task -> onTaskSelected(task) },
                onCheckBoxClicked = { task, isChecked ->
                    onTaskCheckChanged(task, isChecked)
                }
            ),
            onSwipe = { position ->
                val task = _uiState.value.components[position] as TaskUiState
                onTaskSwiped(task)
            },
        )
    }

    /**
     * update sort order based on user prefer
     *
     * @param sortOrder the way user want to sort tasks and folders
     */
    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        updateSortOrderUseCase(sortOrder)
    }

    /**
     * hide or show completed tasks based on user prefer
     *
     * @param hideCompleted do we need to hide completed tasks
     */
    fun onHideCompletedSelected(hideCompleted: Boolean) = viewModelScope.launch {
        updateHideCompletedUseCase(hideCompleted)
    }

    private fun onTaskSelected(task: TaskUiState) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToEditTaskScreen(task.id))
    }

    private fun onTaskCheckChanged(task: TaskUiState, isChecked: Boolean) = viewModelScope.launch {
        updateTaskCheckUseCase(task.id, isChecked)
    }

    private fun onSubFolderSelected(folder: FolderUiState) = viewModelScope.launch {
        navigateToFolder(folderId = folder.id)
    }

    /**
     * Call this function if user press home button
     */
    fun onHomeButtonSelected() = viewModelScope.launch {
        val folderId = _uiState.value.folderData?.parentFolderId ?: return@launch
        navigateToFolder(folderId)
    }

    private fun onTaskSwiped(task: TaskUiState) = viewModelScope.launch {
        deleteTaskUseCase(task.id)
        tasksEventChannel.send(TasksEvent.MessageEvent.ShowUndoDeleteTaskMessage(task))
    }

    private fun onFolderSwiped(folder: FolderUiState, position: Int) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToDeleteFolderScreen(folder))
        tasksEventChannel.send(TasksEvent.NotifyAdapterItemChanged(position))
    }

    /**
     * call this function if user want to undo deletion
     *
     * @param task the deleted task
     */
    fun onUndoDeleteClicked(task: TaskUiState) = viewModelScope.launch {
        addTaskUseCase(
            Task(
                title = task.title,
                parentFolderId = task.parentFolderId,
                isImportant = task.isImportant,
                isCompleted = task.isCompleted,
            )
        )
    }

    /**
     * call this function if user want to add a new task
     */
    fun onAddNewTaskClicked() = viewModelScope.launch {
        _uiState.value.folderData?.let {
            _uiState.value = _uiState.value.copy(fabAnimation = TasksUiState.FABAnimation.DO_NOTHING)
            tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToAddTaskScreen(it.id))
        }
    }

    /**
     * call this function if user want to add a new folder
     */
    fun onAddNewFolderClicked() = viewModelScope.launch {
        val folderData = _uiState.value.folderData ?: return@launch
        _uiState.value = _uiState.value.copy(fabAnimation = TasksUiState.FABAnimation.HIDE_FABS)
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToAddFolderScreen(folderData))
    }

    /**
     * call this function if user clicked the add button
     */
    fun onAddButtonClicked() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            fabAnimation = when (_uiState.value.fabAnimation) {
                TasksUiState.FABAnimation.SHOW_FABS -> TasksUiState.FABAnimation.HIDE_FABS
                else -> TasksUiState.FABAnimation.SHOW_FABS
            }
        )
    }

    /**
     * call this function when a result comes from other fragments
     */
    fun onAddEditResult(result: Int) {
        when (result) {
            ADD_TASK_RESULT_OK -> showTaskSavedConfirmationMessage("Task added")
            EDIT_TASK_RESULT_OK -> showTaskSavedConfirmationMessage("Task updated")
            EDIT_TASK_RESULT_NOTHING_CHANGED -> showTaskSavedConfirmationMessage("Task data didn't change")
        }
    }

    /**
     * call this function when a result comes from other fragments
     */
    fun onAddEditFolderResult(result: Int) = viewModelScope.launch {
        when (result) {
            ADD_FOLDER_RESULT_OK -> showTaskSavedConfirmationMessage("Folder added")
            EDIT_FOLDER_RESULT_OK -> {
                showTaskSavedConfirmationMessage("Folder updated")
            }
        }
    }

    /**
     * quick change of folder
     */
    fun onQuickFolderChangeResult(result: FolderUiState?) = viewModelScope.launch {
        if (result != null) {
            navigateToFolder(result.id)
        }
    }

    private fun showTaskSavedConfirmationMessage(msg: String) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.MessageEvent.ShowTaskSavedConfirmationMessage(msg))
    }

    /**
     * call this function if user want to delete all completed tasks
     */
    fun onDeleteAllCompletedClicked() = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToDeleteAllCompletedScreen)
    }

    /**
     * call this function if user want to navigate to other folder
     */
    fun onQuickFolderChangeClicked() = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToQuickFolderChange(
            getStarredFoldersUseCase().map { it.mapToPresentation() }
        ))
    }

    /**
     * call this function if user want to edit folder information
     */
    fun onEditFolderClicked() = viewModelScope.launch {
        val folderData = _uiState.value.folderData ?: return@launch
        val parentFolder = getFolderUseCase(folderData.parentFolderId).mapToPresentation()
        tasksEventChannel.send(TasksEvent.NavigationEvent.NavigateToEditFolderScreen(parentFolder, folderData))
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

    /**
     * the events to consume by the [TasksFragment]
     */
    sealed class TasksEvent {

        sealed class NavigationEvent {
            data class NavigateToAddTaskScreen(val parentFolderId: Long) : TasksEvent()
            data class NavigateToAddFolderScreen(val parentFolder: FolderUiState) : TasksEvent()

            data class NavigateToEditTaskScreen(val taskId: Long) : TasksEvent()
            data class NavigateToEditFolderScreen(val parentFolder: FolderUiState, val currentFolder: FolderUiState) : TasksEvent()

            object NavigateToDeleteAllCompletedScreen : TasksEvent()
            data class NavigateToDeleteFolderScreen(val folder: FolderUiState) : TasksEvent()

            data class NavigateToQuickFolderChange(val pinnedFolders: List<FolderUiState>) : TasksEvent()
        }

        sealed class MessageEvent {
            data class ShowUndoDeleteTaskMessage(val task: TaskUiState) : TasksEvent()

            data class ShowTaskSavedConfirmationMessage(val msg: String) : TasksEvent()
            data class ShowFolderSavedConfirmationMessage(val msg: String) : TasksEvent()
        }

        data class NotifyAdapterItemChanged(val position: Int) : TasksEvent()

    }

    data class FingerprintsWithAction(
        val fingerprint: ComponentFingerprint<*, *>,
        val onSwipe : (position: Int) -> Unit,
    )

}
