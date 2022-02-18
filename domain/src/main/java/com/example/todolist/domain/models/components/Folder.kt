package com.example.todolist.domain.models.components

/**
 * This class stands for folder
 */
data class Folder constructor(
    override val title: String = "New folder",
    override val parentFolderId: Long,
             val isStarred: Boolean = false,
    override val createdDate: Long = System.currentTimeMillis(),
    override val modifiedDate: Long = System.currentTimeMillis(),
    override val id: Long = 0,
             val subComponents: List<Component> = emptyList(),
) : Component(title, parentFolderId, createdDate, modifiedDate, id)
