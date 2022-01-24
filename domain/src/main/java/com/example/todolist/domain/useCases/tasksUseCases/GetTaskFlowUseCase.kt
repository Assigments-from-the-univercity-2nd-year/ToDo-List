package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskFlowUseCase @Inject constructor(

) {

    operator fun invoke(taskId: Long): Flow<Resource<Task, RepositoryExceptions>> {
        TODO()
    }

}