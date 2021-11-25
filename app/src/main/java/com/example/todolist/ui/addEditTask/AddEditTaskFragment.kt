package com.example.todolist.ui.addEditTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddEditTaskBinding
import com.example.todolist.ui.addEditTask.partsListAdapter.OnPartClickListener
import com.example.todolist.ui.addEditTask.partsListAdapter.PartAdapter
import com.example.todolist.ui.entities.BasePart
import com.example.todolist.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddEditTaskFragment : Fragment(R.layout.fragment_add_edit_task), OnPartClickListener {

    private val viewModel: AddEditTaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditTaskBinding.bind(view)
        val partAdapter = PartAdapter(this)
        binding.apply {
            edittextAddedittaskTaskname.setText(viewModel.taskName)

            checkboxAddedittaskImportance.isChecked = viewModel.taskImportance
            checkboxAddedittaskImportance.jumpDrawablesToCurrentState()

            textviewAddedittaskDatecreated.isVisible = !viewModel.isModifyingTask
            textviewAddedittaskDatecreated.text = "Created: ${viewModel.task.createdDateFormatted}"

            edittextAddedittaskTaskname.addTextChangedListener {
                viewModel.taskName = it.toString()
            }

            checkboxAddedittaskImportance.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.taskImportance = isChecked
            }

            fabFragmentaddedittaskAddbutton.setOnClickListener {
                viewModel.onSaveClicked()
            }

            fabFragmentaddedittaskAddtextpart.setOnClickListener {
                viewModel.onAddTextPartClicked()
            }

            fabFragmentaddedittaskEddtodopart.setOnClickListener {
                viewModel.onAddTodoPartClicked()
            }

            recyclerviewAddedittaskParts.apply {
                adapter = partAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        viewModel.parts.observe(viewLifecycleOwner) {
            partAdapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addEditTaskEvent.collect { event ->
                when (event) {
                    is AddEditTaskViewModel.AddEditTaskEvent.NavigateBackWithResult -> {
                        binding.edittextAddedittaskTaskname.clearFocus()
                        setFragmentResult(
                            "add_edit_request",
                            bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                    is AddEditTaskViewModel.AddEditTaskEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                }.exhaustive
            }
        }
    }

    override fun onStop() {
        super.onStop()

        viewModel.onSaveClicked(false)
    }

    override fun onPartContentChanged(part: BasePart) {
        viewModel.onPartContentChanged(part)
    }
}