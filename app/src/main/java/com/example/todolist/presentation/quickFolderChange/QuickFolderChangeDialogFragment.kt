package com.example.todolist.presentation.quickFolderChange

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.todolist.R
import com.example.todolist.domain.useCases.folderUseCases.GetStarredFoldersUseCase
import com.example.todolist.presentation.entities.components.mapToPresentation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuickFolderChangeDialogFragment @Inject constructor(
    private val getStarredFoldersUseCase: GetStarredFoldersUseCase
) : DialogFragment() {

    private val args: QuickFolderChangeDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val adapter = ArrayAdapter<String>(requireContext(), R.layout.item_folder_pinned)
        adapter.addAll(args.pinnedFolders.map { "${it.title} + (${it.numberOfSubComponents})" })

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
