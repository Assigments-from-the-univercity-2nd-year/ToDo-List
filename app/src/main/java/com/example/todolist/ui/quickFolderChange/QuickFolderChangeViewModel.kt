package com.example.todolist.ui.quickFolderChange

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.example.todolist.R
import com.example.todolist.data.Folder

class QuickFolderChangeViewModel : ViewModel() {

    private lateinit var pinnedFolders: Array<Folder>

    fun getStringOfPinnedFolders(context: Context, arguments: Bundle?): ArrayAdapter<String> {
        val adapter = ArrayAdapter<String>(context, R.layout.item_folder_pinned)
        pinnedFolders = QuickFolderChangeDialogFragmentArgs
            .fromBundle(arguments ?: Bundle.EMPTY)
            .pinnedFolders
        val list = List(pinnedFolders.size) {
            pinnedFolders[it].title + " (${pinnedFolders[it].numberOfSubComponents})"
        }
        adapter.addAll(list)
        return adapter
    }

    fun getListElement(position: Int) = pinnedFolders[position]

}