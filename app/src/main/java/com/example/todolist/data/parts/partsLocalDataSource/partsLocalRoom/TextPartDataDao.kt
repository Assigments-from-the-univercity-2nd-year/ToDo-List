package com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom

import androidx.room.*
import com.example.todolist.data.parts.partsLocalDataSource.entities.TextPartDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TextPartDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTextPartData(textPartData: TextPartDbModel): Long

    @Update
    suspend fun updateTextPartData(textPartData: TextPartDbModel)

    @Delete
    suspend fun deleteTextPartData(textPartData: TextPartDbModel)

    @Query("SELECT * FROM textpart WHERE parentId = :taskId")
    fun getTextPartsOfTask(taskId: Long): Flow<List<TextPartDbModel>>
}