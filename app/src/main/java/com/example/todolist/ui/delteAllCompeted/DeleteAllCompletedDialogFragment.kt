package com.example.todolist.ui.delteAllCompeted

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.todolist.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAllCompletedDialogFragment : DialogFragment() {

    private val viewModel: DeleteAllCompletedViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.deleteallcompleteddialogfragment_confirmdeletion)
            .setMessage(R.string.deleteallcompleteddialogfragment_dialoginfo)
            .setNegativeButton(R.string.deleteallcompleteddialogfragment_cancel, null)
            .setPositiveButton(R.string.deleteallcompleteddialogfragment_confirm) { _, _ ->
                viewModel.onConfirmClicked()
            }
            .create()
}