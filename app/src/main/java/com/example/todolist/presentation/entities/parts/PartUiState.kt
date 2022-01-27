package com.example.todolist.presentation.entities.parts

import com.example.todolist.domain.models.parts.Part

abstract class PartUiState(
    open val content: Any,
    open val position: Int,
    open val parentId: Long,
    open val id: Long,
) {
    abstract fun mapToDomain(): Part

}

fun Part.mapToPresentation(): PartUiState {
    TODO()
}
