package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.models.components.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskFlowUseCase @Inject constructor(

) {

    operator fun invoke(taskId: Long): Flow<Task> {
        TODO()
    }

}
