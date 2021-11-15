package com.example.todolist.ui.quickFolderChange

import android.R.layout.select_dialog_item
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.example.todolist.data.Folder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class QuickFolderChangeViewModel : ViewModel() {

    private lateinit var pinnedFolders: Array<Folder>

    fun getStringOfPinnedFolders(context: Context, arguments: Bundle?): ArrayAdapter<String> {
        val adapter = ArrayAdapter<String>(context, select_dialog_item)
        pinnedFolders = QuickFolderChangeDialogFragmentArgs.fromBundle(arguments ?: Bundle.EMPTY).pinnedFolders
        val list = List(pinnedFolders.size) {
            pinnedFolders[it].title + " (${pinnedFolders[it].numberOfSubComponents})"
        }
        adapter.addAll(list)
        return adapter
    }

    fun getListElement(position: Int) = pinnedFolders[position]

}