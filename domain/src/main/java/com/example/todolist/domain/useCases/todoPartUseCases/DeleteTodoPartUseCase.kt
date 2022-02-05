/*
package com.example.todolist.domain.useCases.todoPartUseCases

import com.example.todolist.domain.models.parts.TodoPart
import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource

class DeleteTodoPartUseCase constructor(
    private val partsRepository: PartsRepository
) {

    suspend operator fun invoke(todoPart: TodoPart): Resource<Unit, RepositoryExceptions> =
        partsRepository.deleteTodoPart(todoPart)

}*/
