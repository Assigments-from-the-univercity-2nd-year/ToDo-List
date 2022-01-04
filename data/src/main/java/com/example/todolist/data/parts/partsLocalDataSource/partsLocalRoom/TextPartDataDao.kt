package com.example.todolist.data.parts.partsLocalDataSource.partsLocalRoom

import androidx.room.*
import com.example.todolist.data.parts.partsLocalDataSource.TextPartDbModelLocalDataSource
import com.example.todolist.data.parts.partsLocalDataSource.entities.TextPartDbModel
import com.example.todolist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

@Dao
interface TextPartDataDao : TextPartDbModelLocalDataSource {

    @Query("SELECT * FROM textpartdbmodel WHERE parentId = :taskId")
    override fun getTextPartsOfTask(taskId: Long): Resource<Flow<List<TextPartDbModel>>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun addTextPart(textPart: TextPartDbModel): Resource<Long>

    @Update
    override suspend fun updateTextPart(textPart: TextPartDbModel): Resource<Unit>

    @Delete
    override suspend fun deleteTextPart(textPart: TextPartDbModel): Resource<Unit>

}