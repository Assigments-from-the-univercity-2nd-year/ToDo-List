package com.example.todolist.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.Task
import com.example.todolist.databinding.ItemTaskBinding

class TasksAdapter(private val onItemClickListener: OnItemClickListener) : ListAdapter<Task, TasksAdapter.TasksViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val binding: ItemTaskBinding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val currentTask = getItem(position)
        holder.bind(currentTask)
    }

    inner class TasksViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClickListener = onItemClickListener
        }

        fun bind(task: Task) {
            binding.task = task
        }

    }

    interface OnItemClickListener {
        fun onItemClicked(task: Task)
        fun onCheckBoxClicked(task: Task, isChecked: Boolean)
    }

    class DiffCallBack : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}