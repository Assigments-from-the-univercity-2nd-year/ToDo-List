package com.example.todolist.data.repositories

import com.example.todolist.domain.models.components.Component
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.repositories.ComponentsRepository
import kotlinx.coroutines.flow.Flow

class ComponentsRepositoryImpl : ComponentsRepository {

    override suspend fun getRootFolder(): Folder {
        TODO("Not yet implemented")
    }

    override fun getComponentsOfFolder(folder: Folder): Flow<List<Component>> {
        TODO("Not yet implemented")
    }

    override fun getPinnedFolders(): Flow<List<Folder>> {
        TODO("Not yet implemented")
    }

    override suspend fun addTask(task: Task): Long {
        TODO("Not yet implemented")
    }

    override suspend fun addFolder(folder: Folder): Long {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun updateFolder(folder: Folder) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCompletedTasks() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFolder(folder: Folder) {
        TODO("Not yet implemented")
    }

}