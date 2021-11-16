package com.example.todolist.ui.addEditFolder

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todolist.databinding.DialogFragmentAddEditFolderBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddEditFolderDialogFragment : DialogFragment() {

    private val viewModel: AddEditFolderViewModel by viewModels()
    private lateinit var binding: DialogFragmentAddEditFolderBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Select folder name")
            .setView(binding.root)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Apply") { _, _ ->
                viewModel.onApplyClicked(
                    binding.edittextModalbottomsheetaddeditfolderFoldername.text.toString(),
                    binding.checkboxModalbottomsheetaddeditfolderPinning.isChecked,
                    AddEditFolderDialogFragmentArgs.fromBundle(
                        arguments ?: Bundle.EMPTY
                    ).parentFolder,
                    AddEditFolderDialogFragmentArgs.fromBundle(
                        arguments ?: Bundle.EMPTY
                    ).currentFolder
                )
            }.create()


    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        binding = DialogFragmentAddEditFolderBinding
            .inflate(LayoutInflater.from(requireContext()), null, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.edittextModalbottomsheetaddeditfolderFoldername.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }
        }

        this.lifecycleScope.launchWhenStarted {
            viewModel.addEditFolderEvent.collect { event ->
                when(event) {
                    is AddEditFolderViewModel.AddEditFolderEvent.NavigateBackWithResult -> {
                        binding.edittextModalbottomsheetaddeditfolderFoldername.clearFocus()
                        setFragmentResult(
                            "add_edit_folder_request",
                            bundleOf("add_edit_folder_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                }
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}