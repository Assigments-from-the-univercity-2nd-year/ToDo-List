package com.example.todolist.domain.models.components

import com.example.todolist.domain.repositories.ComponentsRepository
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

abstract class Component(
    open val title: String,
    open val parentFolderId: Long,
    open val createdDate: Long,
    open val modifiedDate: Long,
    open val id: Long
) {

    val createdDateFormatted: String
        get() = getDateFormatted(createdDate)

    val modifiedDateFormatted: String
        get() = getDateFormatted(modifiedDate)

    //abstract suspend fun delete(componentsRepository: ComponentsRepository)

    //abstract suspend fun update(componentsRepository: ComponentsRepository)

    /*suspend fun getParentFolder(componentsRepository: ComponentsRepository): Folder {
        return componentsRepository.getFolder(this.parentFolderId)
    }*/

    /*protected suspend fun updateModificationDateOfParentFolder(componentsRepository: ComponentsRepository) {
        getParentFolder(componentsRepository)
            .copy(modifiedDate = System.currentTimeMillis())
            .update(componentsRepository)
    }*/

    protected open fun getDateFormatted(date: Long): String {
        return when (System.currentTimeMillis() - date) {
            // less than 1 day
            in 0L..86_400_000L -> {
                SimpleDateFormat("h:mm a", Locale.getDefault()).format(date)
            }

            // less than one year
            in 86_401L..31_556_926_000L -> {
                SimpleDateFormat("MMM d", Locale.getDefault()).format(date)
            }

            else -> DateFormat.getDateInstance(DateFormat.MEDIUM).format(date)
        }
    }

}
