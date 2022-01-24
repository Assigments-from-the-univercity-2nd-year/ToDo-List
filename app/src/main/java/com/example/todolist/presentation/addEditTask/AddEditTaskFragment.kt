package com.example.todolist.presentation.addEditTask

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddEditTaskBinding
import com.example.todolist.presentation.addEditTask.partsListAdapter.OnPartClickListener
import com.example.todolist.presentation.addEditTask.partsListAdapter.PartAdapter
import com.example.todolist.presentation.entities.parts.PartUiState
import com.example.todolist.presentation.entities.parts.TodoPartUiState
import com.example.todolist.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AddEditTaskFragment : Fragment(R.layout.fragment_add_edit_task), OnPartClickListener {

    private val viewModel: AddEditTaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditTaskBinding.bind(view)
        val partAdapter = PartAdapter(this)

        setUpRecyclerView(partAdapter, binding)
        setUpListeners(binding)
        collectEvents(binding)

        viewModel.uiState.observe(viewLifecycleOwner) {
            partAdapter.submitList(it.parts)
            updateTaskState(state = it, binding)
        }
    }

    private fun setUpListeners(binding: FragmentAddEditTaskBinding) {
        binding.apply {
            edittextAddedittaskTaskname.addTextChangedListener {
                viewModel.updateTaskTitle(it.toString())
            }

            checkboxAddedittaskImportance.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateTaskImportance(isChecked)
            }

            fabFragmentaddedittaskAddimagepart.setOnClickListener {
                viewModel.onAddImagePartClicked(
                    requireActivity().activityResultRegistry,
                    requireContext().contentResolver
                )
            }

            fabFragmentaddedittaskAddtextpart.setOnClickListener {
                viewModel.onAddTextPartClicked()
            }

            fabFragmentaddedittaskAddtodopart.setOnClickListener {
                viewModel.onAddTodoPartClicked()
            }
        }
    }

    private fun updateTaskState(state: AddEditTaskUiState, binding: FragmentAddEditTaskBinding) {
        state.taskData?.let { taskUiState ->
            binding.apply {
                edittextAddedittaskTaskname.setText(taskUiState.title)

                checkboxAddedittaskImportance.isChecked = taskUiState.isImportant
                checkboxAddedittaskImportance.jumpDrawablesToCurrentState()

                // TODO: get not .toString but a modifiedDateFormatted
                textviewAddedittaskDatecreated.text = taskUiState.modifiedDate.toString()
            }
        }
        // TODO: if error occurs
    }

    private fun setUpRecyclerView(partAdapter: PartAdapter, binding: FragmentAddEditTaskBinding) {
        binding.recyclerviewAddedittaskParts.apply {
                adapter = partAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

    private fun collectEvents(binding: FragmentAddEditTaskBinding) {
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

    override fun onPartContentChanged(part: PartUiState, newContent: String) {
        viewModel.onPartContentChanged(part, newContent)
    }

    override fun onTodoPartCheckBoxClicked(todoPart: TodoPartUiState, isChecked: Boolean) {
        viewModel.onTodoPartCheckBoxClicked(todoPart, isChecked)
    }

    override fun onDeleteActionSelected(part: PartUiState) {
        viewModel.onDeleteActionSelected(part)
    }

    override fun onMoveUpActionSelected(positionOfMovingPart: Int) {
        viewModel.onMoveUpActionSelected(positionOfMovingPart)
    }

    override fun onMoveDownActionSelected(positionOfMovingPart: Int) {
        viewModel.onMoveDownActionSelected(positionOfMovingPart)
    }

}

const val SELECT_PHOTO = 1
