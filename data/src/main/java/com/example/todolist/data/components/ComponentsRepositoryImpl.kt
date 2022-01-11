package com.example.todolist.data.components

import com.example.todolist.data.components.componentsLocalDataSource.FolderLocalDataSource
import com.example.todolist.data.components.componentsLocalDataSource.TaskLocalDataSource
import com.example.todolist.data.components.componentsLocalDataSource.entities.FolderDbModel
import com.example.todolist.data.components.componentsLocalDataSource.entities.TaskDbModel
import com.example.todolist.domain.models.components.Folder
import com.example.todolist.domain.models.components.FolderCreatingDTO
import com.example.todolist.domain.models.components.Task
import com.example.todolist.domain.repositories.ComponentsRepository
import com.example.todolist.domain.repositories.RepositoryExceptions
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ComponentsRepositoryImpl(
    private val folderLocalDataSource: FolderLocalDataSource,
    private val taskLocalDataSource: TaskLocalDataSource
) : ComponentsRepository {

    override suspend fun getRootFolder(): Resource<Folder, RepositoryExceptions> =
        Resource.Success(folderLocalDataSource.getRootFolder().mapToDomain())

    override fun getSubFoldersOfFolder(folder: Folder): Flow<Resource<List<Folder>, RepositoryExceptions>> =
        folderLocalDataSource.getFoldersOfFolder(folder.mapToData().id).map {
            Resource.Success(it.mapFolderListToDomain())
        }

    override fun getTasksOfFolder(folder: Folder): Flow<Resource<List<Task>, RepositoryExceptions>> =
        taskLocalDataSource.getTasksOfFolder(folder.mapToData().id).map {
            Resource.Success(it.mapTaskListToDomain())
        }

    override fun getPinnedFolders(): Flow<Resource<List<Folder>, RepositoryExceptions>> =
        folderLocalDataSource.getPinnedFolders().map {
            Resource.Success(it.mapFolderListToDomain())
        }

    override suspend fun getParentFolderOfTask(taskId: Long): Resource<Folder, RepositoryExceptions> {
        return Resource.Success(
            folderLocalDataSource.getFolder(
                taskLocalDataSource.getParentFolderIdOfTask(taskId)
            ).mapToDomain()
        )
    }

    override suspend fun getParentFolderOfFolder(folderId: Long): Resource<Folder, RepositoryExceptions> {
        return Resource.Success(
            folderLocalDataSource.getFolder(
                folderLocalDataSource.getParentFolderIdOfFolder(folderId)
            ).mapToDomain()
        )
    }

    override suspend fun addTask(task: Task): Resource<Long, RepositoryExceptions> =
        Resource.Success(taskLocalDataSource.addTask(task.mapToData()))

    override suspend fun addFolder(folderCreatingDTO: FolderCreatingDTO): Resource<Long, RepositoryExceptions> {
        return Resource.Success(
            folderLocalDataSource.addFolder(
                FolderDbModel(
                    title = folderCreatingDTO.title,
                    folderId = folderCreatingDTO.folderId,
                    isPinned = folderCreatingDTO.isPinned
                )
            )
        )
    }

    override suspend fun updateTask(task: Task): Resource<Unit, RepositoryExceptions> =
        Resource.Success(taskLocalDataSource.updateTask(task.mapToData()))

    override suspend fun updateFolder(folder: Folder): Resource<Unit, RepositoryExceptions> =
        Resource.Success(folderLocalDataSource.updateFolder(folder.mapToData()))

    override suspend fun deleteTask(task: Task): Resource<Unit, RepositoryExceptions> =
        Resource.Success(taskLocalDataSource.deleteTask(task.mapToData()))

    override suspend fun deleteCompletedTasks(): Resource<Unit, RepositoryExceptions> =
        Resource.Success(taskLocalDataSource.deleteCompletedTasks())

    override suspend fun deleteFolder(folder: Folder): Resource<Unit, RepositoryExceptions> =
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

    private fun List<FolderDbModel>.mapFolderListToDomain(): List<Folder> =
        this.map {
            it.mapToDomain()
        }

    private fun List<TaskDbModel>.mapTaskListToDomain(): List<Task> =
        this.map {
            it.mapToDomain()
        }

}
