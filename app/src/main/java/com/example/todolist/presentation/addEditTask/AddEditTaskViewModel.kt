package com.example.todolist.presentation.addEditTask

import android.content.ContentResolver
import android.net.Uri
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.Assisted
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.useCases.imagePartUseCases.AddImagePartUseCase
import com.example.todolist.domain.useCases.tasksUseCases.*
import com.example.todolist.domain.useCases.textPartUseCases.AddTextPartUseCase
import com.example.todolist.domain.useCases.todoPartUseCases.AddTodoPartUseCase
import com.example.todolist.domain.useCases.todoPartUseCases.UpdateTodoPartUseCase
import com.example.todolist.presentation.entities.parts.PartUiState
import com.example.todolist.presentation.entities.parts.TodoPartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.FileNotFoundException
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val getPartsOfTaskUseCase: GetPartsOfTaskUseCase,
    private val getTaskFlowUseCase: GetTaskFlowUseCase,

    // Add task
    private val addTaskUseCase: AddTaskUseCase,

    // Update task
    private val updateTaskUseCase: UpdateTaskUseCase,

    // Add part
    private val addTextPartUseCase: AddTextPartUseCase,
    private val addTodoPartUseCase: AddTodoPartUseCase,
    private val addImagePartUseCase: AddImagePartUseCase,

    // Update part
    private val changePartContentUseCase: ChangePartContentUseCase,
    private val updateTodoPartUseCase: UpdateTodoPartUseCase,

    // Delete part
    private val deletePartUseCase: DeletePartUseCase,

    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private val action: AddEditTaskAction = state.get("action") ?: TODO("show user can't load state")

    private val _uiState: MutableStateFlow<AddEditTaskUiState> = MutableStateFlow(AddEditTaskUiState())
    val uiState/*: StateFlow<AddEditTaskUiState>*/ = _uiState.asLiveData()

    private val addEditTaskEventChannel = Channel<AddEditTaskEvent>()
    val addEditTaskEvent = addEditTaskEventChannel.receiveAsFlow()

    init {
        fetchTaskData()
    }

    private fun fetchTaskData() = viewModelScope.launch {
        /*val taskId: Long = getTaskId().onFailure {
            TODO("show message to user")
            return@launch
        }

        combine(getTaskFlowUseCase(taskId), getPartsOfTaskUseCase(taskId)) {
                taskDataRes,
                partsRes ->

            val taskData = taskDataRes.onFailure {
                TODO("show message to user")
                return@combine
            }
            val parts = partsRes.onFailure {
                _uiState.value = AddEditTaskUiState(
                    isLoading = false,
                    taskData = taskData.mapToPresentation(),
                    parts = emptyList()
                )
                TODO("show message to user")
                return@combine
            }

            _uiState.value = AddEditTaskUiState(
                isLoading = false,
                taskData = taskData.mapToPresentation(),
                parts = parts.map { it.mapToPresentation() }
            )
        }*/
    }

    /*private suspend fun getTaskId(): Resource<Long, RepositoryExceptions> {
        return when (action) {
            is AddEditTaskAction.AddTask -> addTaskUseCase(
                TaskCreatingDTO(folderId = action.parentFolderIdForTask)
            )
            is AddEditTaskAction.EditTask -> Resource.Success(action.taskId)
        }
    }*/

    // Adding parts
    fun onAddTextPartClicked()  = viewModelScope.launch {
        /*val taskData = _uiState.value.taskData
        if (taskData != null) {
            addTextPartUseCase(taskData.id).onFailure { TODO() }
        }*/
    }

    fun onAddTodoPartClicked() = viewModelScope.launch {
       /* val taskData = _uiState.value.taskData
        if (taskData != null) {
            addTodoPartUseCase(taskData.id).onFailure { TODO() }
        }*/
    }

    fun onAddImagePartClicked(
        registry: ActivityResultRegistry,
        contentResolver: ContentResolver
    ) = viewModelScope.launch {
        val taskData = _uiState.value.taskData
        if (taskData != null) {
            registry.register("pick_image", ActivityResultContracts.GetContent()) { imageUri ->
                viewModelScope.launch {
                    imagePickerCallback(imageUri, contentResolver, taskData.id)
                }
            }.launch("image")
        }
    }

    private suspend fun imagePickerCallback(
        imageUri: Uri?,
        contentResolver: ContentResolver,
        taskId: Long
    ) {
        try {
            /*val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            addImagePartUseCase(
                bitmap.toByteArray().onFailure { TODO() },
                taskId
            )*/
        } catch (e: FileNotFoundException) {
            e.printStackTrace() // Todo: proper handling
        }
    }

    // Modifying parts
    fun onPartContentChanged(part: PartUiState, newContent: String) = viewModelScope.launch {
        changePartContentUseCase(part.mapToDomain(), newContent)
    }

    fun onTodoPartCheckBoxClicked(todoPart: TodoPartUiState, isChecked: Boolean) = viewModelScope.launch {
        updateTodoPartUseCase(todoPart.copy(isCompleted = isChecked).mapToDomain())
    }

    fun onMoveUpActionSelected(positionOfMovingPart: Int) = viewModelScope.launch {
        // TODO
//        if (positionOfMovingPart > 0) {
//            val movingPart = parts.value?.get(positionOfMovingPart)!!
//            val upPart = parts.value?.get(positionOfMovingPart - 1)!!
//
//            movingPart.update(upPart.position, appRepository)
//            upPart.update(movingPart.position, appRepository)
//        }
    }

    fun onMoveDownActionSelected(positionOfMovingPart: Int) = viewModelScope.launch {
        // TODO
//        if (positionOfMovingPart < parts.value!!.size - 1) {
//            val movingPart = parts.value?.get(positionOfMovingPart)!!
//            val downPart = parts.value?.get(positionOfMovingPart + 1)!!
//
//            movingPart.update(downPart.position, appRepository)
//            downPart.update(movingPart.position, appRepository)
//        }
    }

    // Deleting parts
    fun onDeleteActionSelected(part: PartUiState) = viewModelScope.launch {
        deletePartUseCase(part.mapToDomain())
    }

    // Modifying task
    fun updateTaskTitle(title: String) = viewModelScope.launch {
        //TODO: implement
        /*_uiState.value.taskData?.let {
            updateTaskUseCase(it.copy(title = title).mapToDomain())
        }*/
    }

    fun updateTaskImportance(isImportant: Boolean) = viewModelScope.launch {
        //TODO: implement
        /*_uiState.value.taskData?.let {
            updateTaskUseCase(it.copy(isImportant = isImportant).mapToDomain())
        }*/
    }

    // Making events for Fragment
    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addEditTaskEventChannel.send(AddEditTaskEvent.ShowInvalidInputMessage(text))
    }

    sealed class AddEditTaskEvent {
        data class ShowInvalidInputMessage(val msg: String) : AddEditTaskEvent()
        data class NavigateBackWithResult(val result: Int) : AddEditTaskEvent()
    }
}
