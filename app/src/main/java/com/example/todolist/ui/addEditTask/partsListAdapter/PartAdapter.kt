package com.example.todolist.ui.addEditTask.partsListAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.todolist.databinding.ItemTextPartBinding
import com.example.todolist.ui.entities.BasePart
import com.example.todolist.ui.entities.TextPart

class PartAdapter : ListAdapter<BasePart, PartViewHolder>(PartDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartViewHolder =
        when(viewType) {
            TYPE_TEXT_PART -> {
                val binding =
                    ItemTextPartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PartViewHolder.TextViewHolder(binding)
            }
            TYPE_TODO_PART -> {
                TODO("Not yet implemented")
            }
            TYPE_IMAGE_PART -> {
                TODO("Not yet implemented")
            }
            else -> throw IllegalArgumentException()
        }

    override fun onBindViewHolder(holder: PartViewHolder, position: Int) {
        holder.onBindViewHolder(getItem(position))
    }

    override fun getItemViewType(position: Int): Int =
        when(getItem(position)) {
            is TextPart -> TYPE_TEXT_PART
            else ->  throw IllegalArgumentException() // TODO:
        }
}

private const val TYPE_TEXT_PART = 0
private const val TYPE_TODO_PART = 1
private const val TYPE_IMAGE_PART = 2