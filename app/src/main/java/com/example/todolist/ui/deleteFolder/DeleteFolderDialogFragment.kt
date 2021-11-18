package com.example.todolist.ui.deleteFolder

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteFolderDialogFragment : DialogFragment() {
    private val viewModel: DeleteFolderViewModel by viewModels()
    private val args: DeleteFolderDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm deletion")
            .setMessage("Do you really want to delete this folder?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Delete this folder") { _, _ ->
                viewModel.onDeleteFolderClicked(args.folder)
            }.setNeutralButton("Delete all completed tasks") { _, _ ->
                viewModel.onDeleteCompletedInFolderClicked(args.folder)
            }.create()
}