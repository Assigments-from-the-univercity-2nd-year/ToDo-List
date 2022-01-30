package com.example.todolist.presentation.tasks.componentAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.example.todolist.presentation.entities.components.ComponentUiState

interface ComponentFingerprint<V : ViewBinding, I : ComponentUiState> {

    fun isRelativeItem(item: ComponentUiState): Boolean

    @LayoutRes
    fun getLayoutId(): Int

    fun getViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): ComponentViewHolder<V, I>

}