package com.example.todolist.ui.quickFolderChange

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.todolist.data.Folder
import com.example.todolist.data.FolderDao
import com.example.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class QuickFolderChangeViewModel @ViewModelInject constructor(
    private val folderDao: FolderDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    private lateinit var pinnedFolders: List<Folder>

    init {
        applicationScope.launch {
            pinnedFolders = folderDao.getPinnedFolders()
        }
    }

    fun getStringOfPinnedFolders() = List(pinnedFolders.size) {
        pinnedFolders[it].title + " (${pinnedFolders[it].numberOfSubComponents})"
    }

    fun goToFolder(position: Int) {
        Log.i("TAG", "goToFolder: ${pinnedFolders[position]}")
    }

}