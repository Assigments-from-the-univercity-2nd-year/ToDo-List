package com.example.todolist.presentation.entities.parts

import com.example.todolist.domain.models.parts.Part

abstract class PartUiState(

) {
    abstract fun mapToDomain(): Part

}

fun Part.mapToPresentation(): PartUiState {
    TODO()
}
