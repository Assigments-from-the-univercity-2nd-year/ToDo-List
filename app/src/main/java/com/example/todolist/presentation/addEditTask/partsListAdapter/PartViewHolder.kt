
package com.example.todolist.presentation.addEditTask.partsListAdapter

import android.graphics.BitmapFactory
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.todolist.R
import com.example.todolist.databinding.ItemImagePartBinding
import com.example.todolist.databinding.ItemTextPartBinding
import com.example.todolist.databinding.ItemTodoPartBinding
import com.example.todolist.presentation.entities.components.ComponentUiState
import com.example.todolist.presentation.entities.parts.ImagePartUiState
import com.example.todolist.presentation.entities.parts.PartUiState
import com.example.todolist.presentation.entities.parts.TextPartUiState
import com.example.todolist.presentation.entities.parts.TodoPartUiState

sealed class PartViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    abstract fun onBindViewHolder(basePart: ComponentUiState)

    class TextViewHolder(
        private val binding: ItemTextPartBinding,
        private val onPartClickListener: OnPartClickListener
    ) : PartViewHolder(binding) {
        override fun onBindViewHolder(basePart: ComponentUiState) {
            val currentTextPart = basePart as TextPartUiState

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
                edittextItemtextpartContent.setOnLongClickListener {
                    callMenu(it, onPartClickListener, currentTextPart)
                }
            }
        }
    }

    class TodoViewHolder(
        private val binding: ItemTodoPartBinding,
        private val onPartClickListener: OnPartClickListener
    ) : PartViewHolder(binding) {
        override fun onBindViewHolder(basePart: ComponentUiState) {
            val currentTodoPart = basePart as TodoPartUiState

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
                edittextItemtodopartContent.setOnLongClickListener {
                    callMenu(root, onPartClickListener, currentTodoPart)
                }
            }
        }
    }

    class ImageViewHolder(
        private val binding: ItemImagePartBinding,
        private val onPartClickListener: OnPartClickListener
    ) : PartViewHolder(binding) {
        override fun onBindViewHolder(basePart: ComponentUiState) {
            val currentImagePart = basePart as ImagePartUiState

            binding.apply {
                imageviewItemimagepartContentimage.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                        currentImagePart.content,
                        0,
                        currentImagePart.content.size
                    )
                )
                root.setOnLongClickListener { callMenu(it, onPartClickListener, currentImagePart) }
            }
        }
    }

    protected fun callMenu(view: View, onPartClickListener: OnPartClickListener, basePart: PartUiState): Boolean {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.inflate(R.menu.menu_part)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_menupart_delete -> {
                    onPartClickListener.onDeleteActionSelected(basePart)
                    true
                }
                R.id.action_menupart_moveup -> {
                    onPartClickListener.onMoveUpActionSelected(adapterPosition)
                    true
                }
                R.id.action_menupart_movedown -> {
                    onPartClickListener.onMoveDownActionSelected(adapterPosition)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
        return false
    }
}
