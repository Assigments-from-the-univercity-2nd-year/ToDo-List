package com.example.todolist.presentation.addEditFolder

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolist.R
import com.example.todolist.databinding.DialogFragmentAddEditFolderBinding
import com.example.todolist.presentation.entities.components.FolderUiState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddEditFolderDialogFragment : DialogFragment() {

    private val viewModel: AddEditFolderViewModel by viewModels()
    private val args: AddEditFolderDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogFragmentAddEditFolderBinding.inflate(layoutInflater)
        binding.edittextModalbottomsheetaddeditfolderFoldername
            .setText(args.currentFolder?.title)
        binding.checkboxModalbottomsheetaddeditfolderPinning.isChecked = args.currentFolder?.isStarred ?: false

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.addeditfolderdialogfragment_title)
            .setView(binding.root)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(getPositiveButtonName(args.currentFolder), null)
            .setOnDismissListener {
                viewModel.hideKeyboard(binding.edittextModalbottomsheetaddeditfolderFoldername)
            }
            .create()

        dialog.setOnShowListener {
            binding.edittextModalbottomsheetaddeditfolderFoldername.requestFocus()
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                viewModel.onApplyClicked(binding, args)
            }
        }

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.lifecycleScope.launchWhenStarted {
            viewModel.addEditFolderEvent.collect { event ->
                when(event) {
                    is AddEditFolderViewModel.AddEditFolderEvent.NavigateBackWithResult -> {
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

    private fun getPositiveButtonName(currentFolder: FolderUiState?): Int {
        return if (currentFolder == null) {
            R.string.addeditfolderdialogfragment_addfolder
        } else {
            R.string.addeditfolderdialogfragment_editfolder
        }
    }
}
