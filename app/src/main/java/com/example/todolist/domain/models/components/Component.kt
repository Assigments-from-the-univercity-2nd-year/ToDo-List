package com.example.todolist.domain.models.components

abstract class Component(
    var title: String,
    var folderId: Long,
    var createdDate: Long,
    var modifiedDate: Long,
    var id: Long
) : Cloneable {

    abstract fun delete()

    public override fun clone(): Component {
        return super.clone() as Component
    }
}