package com.example.todolist.ui.addEditTask

import android.content.Intent
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
import com.example.todolist.ui.entities.TodoPart
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

            textviewAddedittaskDatecreated.isVisible = viewModel.isModifyingTask
            textviewAddedittaskDatecreated.text = viewModel.task.modifiedDateFormatted

            edittextAddedittaskTaskname.addTextChangedListener {
                viewModel.taskName = it.toString()
            }

            checkboxAddedittaskImportance.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.taskImportance = isChecked
            }

            fabFragmentaddedittaskAddimagepart.setOnClickListener {
                viewModel.onAddImagePartClicked()
            }

            fabFragmentaddedittaskAddbutton.setOnClickListener {
                viewModel.onSaveClicked()
            }

            fabFragmentaddedittaskAddtextpart.setOnClickListener {
                viewModel.onAddTextPartClicked()
            }

            fabFragmentaddedittaskAddtodopart.setOnClickListener {
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
                    is AddEditTaskViewModel.AddEditTaskEvent.StartActivityForResult -> {
                        startActivityForResult(event.intent, SELECT_PHOTO)
                    }
                }.exhaustive
            }
        }
    }

    override fun onStop() {
        super.onStop()

        viewModel.onSaveClicked(false)
    }

    override fun onPartContentChanged(part: BasePart, newContent: String) {
        viewModel.onPartContentChanged(part, newContent)
    }

    override fun onTodoPartCheckBoxClicked(todoPart: TodoPart, isChecked: Boolean) {
        viewModel.onTodoPartCheckBoxClicked(todoPart, isChecked)
    }

    override fun onDeleteActionSelected(part: BasePart) {
        viewModel.onDeleteActionSelected(part)
    }

    override fun onMoveUpActionSelected(positionOfMovingPart: Int) {
        viewModel.onMoveUpActionSelected(positionOfMovingPart)
    }

    override fun onMoveDownActionSelected(positionOfMovingPart: Int) {
        viewModel.onMoveDownActionSelected(positionOfMovingPart)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data, requireContext().contentResolver)
    }
}

const val SELECT_PHOTO = 1