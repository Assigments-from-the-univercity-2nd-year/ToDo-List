package com.example.todolist.ui.addEditTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddEditTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditTaskFragment : Fragment(R.layout.fragment_add_edit_task) {

    private val viewModel: AddEditTaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditTaskBinding.bind(view)
        binding.apply {
            edittextAddedittaskTaskname.setText(viewModel.taskName)

            checkboxAddedittaskImportance.isChecked = viewModel.taskImportance
            checkboxAddedittaskImportance.jumpDrawablesToCurrentState()

            textviewAddedittaskDatecreated.isVisible = viewModel.task != null
            textviewAddedittaskDatecreated.setText("Created: ${viewModel.task?.createdDateFormatted}")
        }
    }
}