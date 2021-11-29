package com.example.todolist.ui.quickFolderChange

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.example.todolist.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuickFolderChangeDialogFragment : DialogFragment() {

    private val args: QuickFolderChangeDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val adapter = ArrayAdapter<String>(requireContext(), R.layout.item_folder_pinned)
        val list = List(args.pinnedFolders.size) {
            args.pinnedFolders[it].title + " (${args.pinnedFolders[it].numberOfSubComponents})"
        }
        adapter.addAll(list)

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.quickfolderchangedialogfragment_selectthefolder)
            .setAdapter(adapter) { _, which ->
                setFragmentResult(
                    "folder_to_change_request",
                    bundleOf("folder_to_change_result" to args.pinnedFolders[which])
                )
            }.create()
    }
}