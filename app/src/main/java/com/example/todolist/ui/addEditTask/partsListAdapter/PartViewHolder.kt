package com.example.todolist.ui.addEditTask.partsListAdapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.todolist.databinding.ItemImagePartBinding
import com.example.todolist.databinding.ItemTextPartBinding
import com.example.todolist.databinding.ItemTodoPartBinding
import com.example.todolist.ui.entities.BasePart
import com.example.todolist.ui.entities.ImagePart
import com.example.todolist.ui.entities.TextPart
import com.example.todolist.ui.entities.TodoPart

sealed class PartViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    abstract fun onBindViewHolder(basePart: BasePart)

    class TextViewHolder(
        private val binding: ItemTextPartBinding,
        private val onPartClickListener: OnPartClickListener
    ) : PartViewHolder(binding) {
        override fun onBindViewHolder(basePart: BasePart) {
            val currentTextPart = basePart as TextPart

            binding.apply {
                edittextItemtextpartContent.setText(currentTextPart.content)
                edittextItemtextpartContent.setOnFocusChangeListener { view, hasFocus ->
                    if (!hasFocus) {
                        onPartClickListener.onPartContentChanged(
                            currentTextPart,
                            edittextItemtextpartContent.text.toString()
                        )
                    }
                }
            }
        }
    }

    class TodoViewHolder(
        private val binding: ItemTodoPartBinding,
        private val onPartClickListener: OnPartClickListener
    ) : PartViewHolder(binding) {
        override fun onBindViewHolder(basePart: BasePart) {
            val currentTodoPart = basePart as TodoPart

            binding.apply {
                edittextItemtodopartContent.setText(currentTodoPart.content)
                edittextItemtodopartContent.paint.isStrikeThruText = currentTodoPart.isCompleted
                checkboxItemtodopartCompleted.isChecked = currentTodoPart.isCompleted
                edittextItemtodopartContent.setOnFocusChangeListener { view, hasFocus ->
                    if (!hasFocus) {
                        onPartClickListener.onPartContentChanged(
                            currentTodoPart,
                            edittextItemtodopartContent.text.toString()
                        )
                    }
                }
                checkboxItemtodopartCompleted.setOnClickListener {
                    onPartClickListener.onTodoPartCheckBoxClicked(
                        currentTodoPart,
                        checkboxItemtodopartCompleted.isChecked
                    )
                }
            }
        }
    }

    class ImageViewHolder(
        private val binding: ItemImagePartBinding,
        private val onPartClickListener: OnPartClickListener
    ) : PartViewHolder(binding) {
        override fun onBindViewHolder(basePart: BasePart) {
            val currentImagePart = basePart as ImagePart

            binding.apply {
                imageviewItemimagepartContentimage.setImageBitmap(currentImagePart.content)
            }
        }
    }
}