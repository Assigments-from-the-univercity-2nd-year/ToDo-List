package com.example.todolist.data.components

import android.util.Log
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ComponentsRepositoryImpl(
    private val folderLocalDataSource: FolderLocalDataSource,
    private val taskLocalDataSource: TaskLocalDataSource
) : ComponentsRepository {

    override suspend fun getRootFolder(): Resource<Folder, RepositoryExceptions> {
        return try {
            Resource.Success(folderLocalDataSource.getRootFolder().mapToDomain())
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override fun getSubFoldersOfFolder(folder: Folder): Flow<Resource<List<Folder>, RepositoryExceptions>> {
        return folderLocalDataSource.getFoldersOfFolder(folder.mapToData().id)
            .map { Resource.Success(it.mapFolderListToDomain()) as Resource<List<Folder>, RepositoryExceptions>}
            .catch { exception ->
                Log.e(TAG, "getTextPartsOfTask: ", exception)
                emit(Resource.Failure(reason = RepositoryExceptions.UnknownException(exception.cause)))
            }
    }

    override fun getTasksOfFolder(folder: Folder): Flow<Resource<List<Task>, RepositoryExceptions>> {
        return taskLocalDataSource.getTasksOfFolder(folder.mapToData().id)
            .map { Resource.Success(it.mapTaskListToDomain()) as Resource<List<Task>, RepositoryExceptions>}
            .catch { exception ->
                Log.e(TAG, "getTextPartsOfTask: ", exception)
                emit(Resource.Failure(reason = RepositoryExceptions.UnknownException(exception.cause)))
            }
    }

    override fun getPinnedFolders(): Flow<Resource<List<Folder>, RepositoryExceptions>> {
        return folderLocalDataSource.getPinnedFolders()
            .map { Resource.Success(it.mapFolderListToDomain()) as Resource<List<Folder>, RepositoryExceptions>}
            .catch { exception ->
                Log.e(TAG, "getTextPartsOfTask: ", exception)
                emit(Resource.Failure(reason = RepositoryExceptions.UnknownException(exception.cause)))
            }
    }

    override suspend fun getParentFolderOfTask(taskId: Long): Resource<Folder, RepositoryExceptions> {
        return try {
            Resource.Success(
                folderLocalDataSource.getFolder(
                    taskLocalDataSource.getParentFolderIdOfTask(taskId)
                ).mapToDomain()
            )
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun getParentFolderOfFolder(folderId: Long): Resource<Folder, RepositoryExceptions> {
        return try {
            Resource.Success(
                folderLocalDataSource.getFolder(
                    folderLocalDataSource.getParentFolderIdOfFolder(folderId)
                ).mapToDomain()
            )
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun addTask(task: Task): Resource<Long, RepositoryExceptions> {
        return try {
            Resource.Success(taskLocalDataSource.addTask(task.mapToData()))
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun addFolder(folderCreatingDTO: FolderCreatingDTO): Resource<Long, RepositoryExceptions> {
        return try {
            Resource.Success(
                folderLocalDataSource.addFolder(
                    FolderDbModel(
                        title = folderCreatingDTO.title,
                        folderId = folderCreatingDTO.folderId,
                        isPinned = folderCreatingDTO.isPinned

                    )
                )
            )
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun updateTask(task: Task): Resource<Unit, RepositoryExceptions> {
        return try {
            Resource.Success(taskLocalDataSource.updateTask(task.mapToData()))
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun updateFolder(folder: Folder): Resource<Unit, RepositoryExceptions> {
        return try {
            Resource.Success(folderLocalDataSource.updateFolder(folder.mapToData()))
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun deleteTask(task: Task): Resource<Unit, RepositoryExceptions> {
        return try {
            Resource.Success(taskLocalDataSource.deleteTask(task.mapToData()))
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun deleteCompletedTasks(): Resource<Unit, RepositoryExceptions> {
        return try {
            Resource.Success(taskLocalDataSource.deleteCompletedTasks())
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

    override suspend fun deleteFolder(folder: Folder): Resource<Unit, RepositoryExceptions> {
        return try {
            Resource.Success(folderLocalDataSource.deleteFolder(folder.mapToData()))
        } catch (exception: Exception) {
            Resource.Failure(RepositoryExceptions.UnknownException(cause = exception.cause))
        }
    }

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

    private fun List<FolderDbModel>.mapFolderListToDomain(): List<Folder> {
        return this.map {
            it.mapToDomain()
        }
    }

    private fun List<TaskDbModel>.mapTaskListToDomain(): List<Task> {
        return this.map {
            it.mapToDomain()
        }
    }

    private companion object {
        private const val TAG = "ComponentsRepoImpl"
    }

}
