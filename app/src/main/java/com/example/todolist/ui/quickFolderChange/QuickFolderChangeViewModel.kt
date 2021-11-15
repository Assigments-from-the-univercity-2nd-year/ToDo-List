package com.example.todolist.ui.quickFolderChange

import android.R.layout.select_dialog_item
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.todolist.data.FolderDao
import com.example.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope

class QuickFolderChangeViewModel @ViewModelInject constructor(
    private val folderDao: FolderDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun getStringOfPinnedFolders(context: Context, arguments: Bundle?): ArrayAdapter<String> {
        val adapter = ArrayAdapter<String>(context, select_dialog_item)
        val pinnedFolders = QuickFolderChangeDialogFragmentArgs.fromBundle(arguments ?: Bundle.EMPTY).pinnedFolders
        val list = List(pinnedFolders.size) {
            pinnedFolders[it].title + " (${pinnedFolders[it].numberOfSubComponents})"
        }
        adapter.addAll(list)
        return adapter
    }

    fun goToFolder(position: Int) {
        // TODO: 15.11.2021
    }

}