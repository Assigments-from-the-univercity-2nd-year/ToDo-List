package com.example.todolist.data.components

import com.example.todolist.data.components.componentsLocalDataSource.FolderLocalDataSource
import com.example.todolist.data.components.componentsLocalDataSource.TaskLocalDataSource
import com.example.todolist.data.components.componentsLocalDataSource.entities.FolderDbModel
import com.example.todolist.data.components.componentsLocalDataSource.entities.TaskDbModel
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ComponentsRepositoryImpl(
    private val folderLocalDataSource: FolderLocalDataSource,
    private val taskLocalDataSource: TaskLocalDataSource
) : ComponentsRepository {

    override suspend fun getRootFolder(): Resource<Folder> =
        Resource.Success(folderLocalDataSource.getRootFolder().mapToDomain())

    override fun getSubFoldersOfFolder(folder: Folder): Resource<Flow<List<Folder>>> =
        Resource.Success(folderLocalDataSource.getFoldersOfFolder(folder.mapToData().id).mapFolderFlowToDomain())

    override fun getTasksOfFolder(folder: Folder): Resource<Flow<List<Task>>> =
        Resource.Success(taskLocalDataSource.getTasksOfFolder(folder.mapToData().id).mapTaskFlowToDomain())

    override fun getPinnedFolders(): Resource<Flow<List<Folder>>> =
        Resource.Success(folderLocalDataSource.getPinnedFolders().mapFolderFlowToDomain())

    override suspend fun addTask(task: Task): Resource<Long> =
        Resource.Success(taskLocalDataSource.addTask(task.mapToData()))

    override suspend fun addFolder(folder: Folder): Resource<Long> =
        Resource.Success(folderLocalDataSource.addFolder(folder.mapToData()))

    override suspend fun updateTask(task: Task): Resource<Unit> =
        Resource.Success(taskLocalDataSource.updateTask(task.mapToData()))

    override suspend fun updateFolder(folder: Folder): Resource<Unit> =
        Resource.Success(folderLocalDataSource.updateFolder(folder.mapToData()))

    override suspend fun deleteTask(task: Task): Resource<Unit> =
        Resource.Success(taskLocalDataSource.deleteTask(task.mapToData()))

    override suspend fun deleteCompletedTasks(): Resource<Unit> =
        Resource.Success(taskLocalDataSource.deleteCompletedTasks())

    override suspend fun deleteFolder(folder: Folder): Resource<Unit> =
        Resource.Success(folderLocalDataSource.deleteFolder(folder.mapToData()))

    private fun FolderDbModel.mapToDomain(): Folder {
        TODO("Not yet implemented")
    }

    private fun Folder.mapToData(): FolderDbModel {
        TODO("Not yet implemented")
    }

    private fun TaskDbModel.mapToDomain(): Task {
        TODO("Not yet implemented")
    }

    private fun Task.mapToData(): TaskDbModel {
        TODO("Not yet implemented")
    }

    private fun Flow<List<FolderDbModel>>.mapFolderFlowToDomain(): Flow<List<Folder>> =
        this.map { list ->
            list.map {
                it.mapToDomain()
            }
        }

    private fun Flow<List<TaskDbModel>>.mapTaskFlowToDomain(): Flow<List<Task>> =
        this.map { list ->
            list.map {
                it.mapToDomain()
            }
        }

}