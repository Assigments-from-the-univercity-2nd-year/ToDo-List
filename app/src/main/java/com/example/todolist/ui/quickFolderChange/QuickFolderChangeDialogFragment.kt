package com.example.todolist.ui.quickFolderChange

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuickFolderChangeDialogFragment : DialogFragment() {

    private val viewModel: QuickFolderChangeViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Select the folder")
            .setItems(viewModel.getStringOfPinnedFolders().toTypedArray()) { dialog, which ->
                viewModel.goToFolder(which)
            }.create()
}