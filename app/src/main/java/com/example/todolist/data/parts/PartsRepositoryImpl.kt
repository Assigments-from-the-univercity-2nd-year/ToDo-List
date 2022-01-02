package com.example.todolist.data.parts

import com.example.todolist.data.parts.partsLocalDataSource.ImagePartLocalDataSource
import com.example.todolist.data.parts.partsLocalDataSource.TextPartLocalDataSource
import com.example.todolist.data.parts.partsLocalDataSource.TodoPartLocalDataSource
import com.example.todolist.domain.models.parts.Part
import com.example.todolist.domain.repositories.PartsRepository
import com.example.todolist.ui.entities.ImagePart
import com.example.todolist.ui.entities.TextPart
import com.example.todolist.ui.entities.TodoPart
import kotlinx.coroutines.flow.Flow

class PartsRepositoryImpl(
    private val textPartLocalDataSource: TextPartLocalDataSource,
    private val imagePartLocalDataSource: ImagePartLocalDataSource,
    private val todoPartLocalDataSource: TodoPartLocalDataSource
) : PartsRepository {

    override fun getPartsOfTask(taskId: Long): Flow<List<Part>> {
        TODO("Not yet implemented")
    }

    override suspend fun addTextPart(textPart: TextPart): Long {
        TODO("Not yet implemented")
    }

    override suspend fun addTodoPart(todoPart: TodoPart): Long {
        TODO("Not yet implemented")
    }

    override suspend fun addImagePart(imagePart: ImagePart): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTextPart(textPart: TextPart) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodoPart(todoPart: TodoPart) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteImagePart(imagePart: ImagePart) {
        TODO("Not yet implemented")
    }

}