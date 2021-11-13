package com.example.todolist.ui.addEditFolder

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.todolist.databinding.DialogFragmentAddEditFolderBinding
import dagger.hilt.android.AndroidEntryPoint

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
                viewModel.applyFolder(
                    binding.edittextModalbottomsheetaddeditfolderFoldername.text.toString(),
                    AddEditFolderDialogFragmentArgs.fromBundle(
                        arguments ?: Bundle.EMPTY
                    ).parentFolder
                )
            }.create()


    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        binding = DialogFragmentAddEditFolderBinding
            .inflate(LayoutInflater.from(requireContext()), null, false)
    }
}