package com.example.todolist.ui.tasks

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
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
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks), OnComponentClickListener {

    private val viewModel: TasksViewModel by viewModels()
    private lateinit var searchView: SearchView

    private val rotateOpenAnim: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open_anim) }
    private val rotateCloseAnim: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close_anim) }
    private val fromBottomAnim: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim) }
    private val toBottomAnim: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim) }

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

            ItemTouchHelper(SwipingSimpleCallback(
                taskAdapter, viewModel
            )).attachToRecyclerView(recyclerviewFragmenttasksTasks)

            ItemTouchHelper(MovingSimpleCallback(
                taskAdapter, viewModel
            )).attachToRecyclerView(recyclerviewFragmenttasksTasks)

            fabFragmenttasksAddbutton.setOnClickListener {
                viewModel.onAddButtonClicked()
            }
            // TODO: 13.11.2021 clear animation from the previous time (add FAB rotation)

            fabFragmenttasksAddtask.setOnClickListener {
                viewModel.onAddNewTaskClicked()
                viewModel.onAddButtonClicked.value = false
            }

            fabFragmenttasksAddfolder.setOnClickListener {
                viewModel.onAddNewFolderClicked()
                viewModel.onAddButtonClicked.value = false
            }
        }

        viewModel.onAddButtonClicked.observe(viewLifecycleOwner) {
            setVisibility(it)
            setAnimation(it)
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
                                viewModel.onUndoDeleteClicked(event.task, event.parentFolder)
                            }.show()
                    }
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToAddTaskScreen -> {
                        val action =
                            TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                                title = "New Task",
                                folderId = viewModel.currentFolder.value?.id ?: 1L
                            )
                        findNavController().navigate(action)
                    }
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToEditTaskScreen -> {
                        val action =
                            TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                                event.task,
                                "Edit Task",
                                viewModel.currentFolder.value?.id ?: 1L
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
                    is TasksViewModel.TasksEvent.NotifyAdapterItemChanged -> {
                        taskAdapter.notifyItemChanged(event.position)
                    }
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToDeleteFolderScreen -> {
                        val action = TasksFragmentDirections.actionGlobalDeleteFolderDialogFragment(event.folder)
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }

        val onBackPressedCallback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                viewModel.onHomeButtonSelected()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )

        // showing back button manually
        viewModel.currentFolder.observe(viewLifecycleOwner) {
            if (it.id != 1L) { // 1L is an id of the root folder
                (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                onBackPressedCallback.isEnabled = true
            } else {
                (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
                onBackPressedCallback.isEnabled = false
            }

            (activity as? AppCompatActivity)?.supportActionBar?.title = it.title
        }

        setHasOptionsMenu(true)
    }

    private fun setVisibility(clicked: Boolean) {
        if (clicked) {
            fab_fragmenttasks_addtask.visibility = View.VISIBLE
            fab_fragmenttasks_addfolder.visibility = View.VISIBLE
        } else {
            fab_fragmenttasks_addtask.visibility = View.INVISIBLE
            fab_fragmenttasks_addfolder.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (clicked) {
            fab_fragmenttasks_addbutton.startAnimation(rotateOpenAnim)
            fab_fragmenttasks_addtask.startAnimation(fromBottomAnim)
            fab_fragmenttasks_addfolder.startAnimation(fromBottomAnim)
        } else {
            fab_fragmenttasks_addbutton.startAnimation(rotateCloseAnim)
            fab_fragmenttasks_addtask.startAnimation(toBottomAnim)
            fab_fragmenttasks_addfolder.startAnimation(toBottomAnim)
        }
    }

    override fun onCheckBoxClicked(task: Task, isChecked: Boolean) {
        viewModel.onTaskCheckChanged(task, isChecked)
    }

    override fun onFolderClicked(folder: Folder) {
        viewModel.onSubFolderSelected(folder)
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
            android.R.id.home -> {
                viewModel.onHomeButtonSelected()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
    }
}