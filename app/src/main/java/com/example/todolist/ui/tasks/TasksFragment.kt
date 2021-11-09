package com.example.todolist.ui.tasks

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.Folder
import com.example.todolist.data.SortOrder
import com.example.todolist.data.Task
import com.example.todolist.databinding.FragmentTasksBinding
import com.example.todolist.ui.tasks.componentListAdapter.ComponentAdapter
import com.example.todolist.ui.tasks.componentListAdapter.OnComponentClickListener
import com.example.todolist.util.exhaustive
import com.example.todolist.util.onQueryTextChanged
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks), OnComponentClickListener {

    private val viewModel: TasksViewModel by viewModels()
    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTasksBinding.bind(view)
        val taskAdapter = ComponentAdapter(this)
        binding.apply {
            recyclerviewFragmenttasksTasks.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val component = taskAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.onComponentSwiped(component, viewHolder.adapterPosition)
                }
            }).attachToRecyclerView(recyclerviewFragmenttasksTasks)

            fabFragmenttasksAddtask.setOnClickListener {
                viewModel.onAddNewTaskClicked()
            }
        }

        setFragmentResultListener("add_edit_request") { s: String, bundle: Bundle ->
            val result = bundle.getInt("add_edit_result")
            viewModel.onAddEditResult(result)
        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.tasksEvent.collect { event ->
                when (event) {
                    is TasksViewModel.TasksEvent.MessageEvent.ShowUndoDeleteTaskMessage -> {
                        Snackbar.make(requireView(), "Task deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO") {
                                viewModel.onUndoDeleteClicked(event.task)
                            }.show()
                    }
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToAddTaskScreen -> {
                        val action =
                            TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                                title = "New Task",
                                folderId = viewModel.currentFolderId
                            )
                        findNavController().navigate(action)
                    }
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToEditTaskScreen -> {
                        val action =
                            TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                                event.task,
                                "Edit Task",
                                viewModel.currentFolderId
                            )
                        findNavController().navigate(action)
                    }
                    is TasksViewModel.TasksEvent.MessageEvent.ShowTaskSavedConfirmationMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToDeleteAllCompletedScreen -> {
                        val action = TasksFragmentDirections.actionGlobalDeleteAllCompletedDialogFragment()
                        findNavController().navigate(action)
                    }
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToFolderScreen -> {
                        val action = TasksFragmentDirections.actionTasksFragmentSelf(event.folder)
                        findNavController().navigate(action)
                    }
                    is TasksViewModel.TasksEvent.NotifyAdapterItemChanged -> {
                        taskAdapter.notifyItemChanged(event.position)
                    }
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToDeleteFolderScreen -> {
                        val action = TasksFragmentDirections.actionGlobalDeleteFolderDialogFragment()
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }

        // showing back button manually
        if (viewModel.currentFolderId != 1L) { // 1L is an id of the root folder
            (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        setHasOptionsMenu(true)
    }

    override fun onCheckBoxClicked(task: Task, isChecked: Boolean) {
        viewModel.onTaskCheckChanged(task, isChecked)
    }

    override fun onFolderClicked(folder: Folder) {
        viewModel.onFolderSelected(folder)
    }

    override fun onTaskClicked(task: Task) {
        viewModel.onTaskSelected(task)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_tasks, menu)

        val searchItem = menu.findItem(R.id.action_menuFragmentTasks_search)
        searchView = searchItem.actionView as SearchView

        val pendingQuery = viewModel.searchQuery.value
        if (pendingQuery != null && pendingQuery.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }

        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it
        }

        viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.action_menuFragmentTasks_hideCompletedTasks).isChecked =
                viewModel.preferencesFlow.first().hideCompleted
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_menuFragmentTasks_sortByName -> {
                viewModel.onSortOrderSelected(SortOrder.BY_NAME)
                true
            }
            R.id.action_menuFragmentTasks_sortByDateCreated -> {
                viewModel.onSortOrderSelected(SortOrder.BY_DATE)
                true
            }
            R.id.action_menuFragmentTasks_hideCompletedTasks -> {
                item.isChecked = !item.isChecked
                viewModel.onHideCompletedSelected(item.isChecked)
                true
            }
            R.id.action_menuFragmentTasks_deleteAllCompletedTasks -> {
                viewModel.onDeleteAllCompletedClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
    }
}