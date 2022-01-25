package com.example.todolist.presentation.deleteFolder

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.todolist.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteFolderDialogFragment : DialogFragment() {
    private val viewModel: DeleteFolderViewModel by viewModels()
    private val args: DeleteFolderDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.deletefolderdialogfragment_title)
            .setMessage(R.string.deletefolderdialogfragment_message)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.deletefolderdialogfragment_deletefolder) { _, _ ->
                viewModel.onDeleteFolderClicked(args.folder)
            }.setNeutralButton(R.string.deletefolderdialogfragment_deleteallcomplitedtastks) { _, _ ->
                viewModel.onDeleteCompletedInFolderClicked(args.folder)
            }.create()
}
