package com.example.todolist.ui.addEditTask

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.componentsDB.FolderDao
import com.example.todolist.data.componentsDB.Task
import com.example.todolist.data.componentsDB.TaskDao
import com.example.todolist.data.repository.AppRepository
import com.example.todolist.ui.ADD_TASK_RESULT_OK
import com.example.todolist.ui.EDIT_TASK_RESULT_OK
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEditTaskViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    private val folderDao: FolderDao,
    private val appRepository: AppRepository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private val folderId = state.get<Long>("folderId")
    val task = state.get<Task>("task") ?: Task("", folderId!!)
    val isCreatingTask: Boolean
        get() = state.get<Task>("task") == null

    var taskName = state.get<String>("taskName") ?: task.title ?: ""
        set(value) {
            field = value
            state.set("taskName", value)
        }

    var taskImportance = state.get<Boolean>("taskImportance") ?: task.isImportant
        set(value) {
            field = value
            state.set("taskImportance", value)
        }

    private val partsFlow = appRepository.getTextPartsOfTask(task.id)
    val parts = partsFlow.asLiveData()

    private val addEditTaskEventChannel = Channel<AddEditTaskEvent>()
    val addEditTaskEvent = addEditTaskEventChannel.receiveAsFlow()

    fun onSaveClicked() {
        if (taskName.isBlank()) {
            showInvalidInputMessage("Name can not be empty.")
            return
        }

        if (isCreatingTask) {
            val updatedTask = task.copy(
                title = taskName,
                isImportant = taskImportance,
                modifiedDate = System.currentTimeMillis()
            )
            updateTask(updatedTask)
            updateFolder()
        } else {
            val newTask = Task(taskName, folderId!!, taskImportance)
            insertTask(newTask)
            updateFolder()
        }
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addEditTaskEventChannel.send(AddEditTaskEvent.ShowInvalidInputMessage(text))
    }

    private fun updateTask(task: Task) = viewModelScope.launch {
        taskDao.updateTask(task)
        addEditTaskEventChannel.send(AddEditTaskEvent.NavigateBackWithResult(EDIT_TASK_RESULT_OK))
    }

    private fun insertTask(task: Task) = viewModelScope.launch {
        taskDao.insertTask(task)
        addEditTaskEventChannel.send(AddEditTaskEvent.NavigateBackWithResult(ADD_TASK_RESULT_OK))
    }

    private fun updateFolder() = viewModelScope.launch {
        val folder = folderDao.getFolder(folderId!!)
        folderDao.updateFolder(folder.copy(modifiedDate = System.currentTimeMillis()))
    }

    sealed class AddEditTaskEvent {
        data class ShowInvalidInputMessage(val msg: String) : AddEditTaskEvent()
        data class NavigateBackWithResult(val result: Int) : AddEditTaskEvent()
    }
}