package com.example.todolist.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.FragmentTasksBinding
import com.example.todolist.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private val viewModel: TasksViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTasksBinding.bind(view)
        binding.adapter = TasksAdapter()
        binding.layoutManager = LinearLayoutManager(requireContext())
        //TODO: setHasFixedSize(true)
        viewModel.tasks.observe(viewLifecycleOwner){
            binding.adapter?.submitList(it)
            //it.let { adapter::submitlist }
            //it?.let(binding.adapter::submitList())

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_tasks, menu)

        val searchItem = menu.findItem(R.id.action_menuFragmentTasks_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            TODO("update search query")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_menuFragmentTasks_sortByName -> {
                true
            }
            R.id.action_menuFragmentTasks_sortByDateCreated -> {
                true
            }
            R.id.action_menuFragmentTasks_hideCompletedTasks -> {
                item.isChecked = !item.isChecked
                true
            }
            R.id.action_menuFragmentTasks_deleteAllCompletedTasks -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}