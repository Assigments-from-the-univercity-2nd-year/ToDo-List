package com.example.todolist.domain.useCases.tasksUseCases

import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.models.parts.Part
import kotlinx.coroutines.flow.Flow

class GetPartsOfTaskUseCase {

    operator fun invoke(task: Task): Flow<List<Part>> {
        TODO()
    }

}