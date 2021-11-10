package com.example.todolist.ui.tasks

import com.example.todolist.data.Component
import com.example.todolist.data.Task

class CompareComponents {
    companion object : Comparator<Component> {
        override fun compare(o1: Component?, o2: Component?) = when {
            (o1 as? Task)?.isImportant ?: false -> {
                if ((o2 as? Task)?.isImportant ?: false) {
                    0
                } else {
                    -1
                }
            }
            (o2 as? Task)?.isImportant ?: false -> {
                1
            }
            else -> 0
        }

    }
}