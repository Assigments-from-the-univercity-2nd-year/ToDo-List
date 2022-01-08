package com.example.todolist.presentation.addEditTask.partsListAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.todolist.databinding.ItemImagePartBinding
import com.example.todolist.databinding.ItemTextPartBinding
import com.example.todolist.databinding.ItemTodoPartBinding
import com.example.todolist.presentation.entities.BasePart
import com.example.todolist.presentation.entities.ImagePart
import com.example.todolist.presentation.entities.TextPart
import com.example.todolist.presentation.entities.TodoPart

class PartAdapter(
    private val onPartClickListener: OnPartClickListener
) : ListAdapter<BasePart, PartViewHolder>(PartDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartViewHolder =
        when(viewType) {
            TYPE_TEXT_PART -> {
                val binding =
                    ItemTextPartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PartViewHolder.TextViewHolder(binding, onPartClickListener)
            }
            TYPE_TODO_PART -> {
                val binding =
                    ItemTodoPartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PartViewHolder.TodoViewHolder(binding, onPartClickListener)
            }
            TYPE_IMAGE_PART -> {
                val binding =
                    ItemImagePartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PartViewHolder.ImageViewHolder(binding, onPartClickListener)
            }
            else -> throw IllegalArgumentException()
        }

    override fun onBindViewHolder(holder: PartViewHolder, position: Int) {
        holder.onBindViewHolder(getItem(position))
    }

    override fun getItemViewType(position: Int): Int =
        when(getItem(position)) {
            is TextPart -> TYPE_TEXT_PART
            is TodoPart -> TYPE_TODO_PART
            is ImagePart -> TYPE_IMAGE_PART
        }
}

private const val TYPE_TEXT_PART = 0
private const val TYPE_TODO_PART = 1
private const val TYPE_IMAGE_PART = 2