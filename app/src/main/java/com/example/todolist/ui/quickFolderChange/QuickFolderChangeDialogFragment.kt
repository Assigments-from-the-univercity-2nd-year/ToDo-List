package com.example.todolist.ui.quickFolderChange

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuickFolderChangeDialogFragment : DialogFragment() {

    private val viewModel: QuickFolderChangeViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Select the folder")
            .setAdapter(viewModel.getStringOfPinnedFolders(requireContext(), arguments)) { _, which ->
                setFragmentResult(
                    "folder_to_change_request",
                    bundleOf("folder_to_change_result" to viewModel.getListElement(which))
                )
            }.create()
}