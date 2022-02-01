package com.example.todolist.presentation.tasks

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.FragmentTasksBinding
import com.example.todolist.presentation.entities.components.FolderUiState
import com.example.todolist.presentation.tasks.componentAdapter.ComponentAdapter
import com.example.todolist.presentation.tasks.componentAdapter.ComponentFingerprint
import com.example.todolist.presentation.tasks.componentAdapter.folder.FolderFingerprint
import com.example.todolist.presentation.tasks.componentAdapter.task.TaskFingerprint
import com.example.todolist.presentation.tasks.componentAdapter.itemDecorations.HorizontalItemDecoration
import com.example.todolist.presentation.tasks.componentAdapter.itemDecorations.VerticalItemDecoration
import com.example.todolist.presentation.tasks.simpleCallbacks.SwipingSimpleCallback
import com.example.todolist.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private val viewModel: TasksViewModel by viewModels()
    //private lateinit var searchView: SearchView
    /*private val onBackPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            viewModel.onHomeButtonSelected()
        }
    }*/
    /*private val onDestinationChangedListener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == controller.graph.startDestination) {
                if (viewModel.currentFolder.value?.id ?: 1L != 1L) { // 1L is an id of the root folder
                    (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    onBackPressedCallback.isEnabled = true
                } else {
                    (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    onBackPressedCallback.isEnabled = false
                }

                (activity as? AppCompatActivity)?.supportActionBar?.title = viewModel.currentFolder.value?.title
            }
        }*/

    //private val fromBottomAnim: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim) }
    //private val toBottomAnim: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTasksBinding.bind(view)
        val componentAdapter = ComponentAdapter(getFingerprints())

        setUpRecyclerView(componentAdapter, binding)
        setUpListeners(binding)
        setUpFragmentResultListeners()
        collectEvents()

        /*binding.apply {
            ItemTouchHelper(MovingSimpleCallback(
                taskAdapter, viewModel
            )).attachToRecyclerView(recyclerviewFragmenttasksTasks)
        }*/

        /*viewModel.onAddButtonClicked.observe(viewLifecycleOwner) {
            if (it == TasksViewModel.FABAnimation.SHOW_FABS) {
                setVisibility(true)
                setAnimation(true)
            } else if (it == TasksViewModel.FABAnimation.HIDE_FABS) {
                setVisibility(false)
                setAnimation(false)
            }
        }*/

        /*requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )*/

        //findNavController().addOnDestinationChangedListener(onDestinationChangedListener)

        viewModel.uiState.observe(viewLifecycleOwner) {
            if (it.folderData != null) {
                componentAdapter.submitList(it.components)
                enableBackButton(viewModel.isCurrentFolderRoot())
                (activity as? AppCompatActivity)?.supportActionBar?.title =
                        viewModel.getTitleName(resources.getString(R.string.taskfragment_all_tasks_title))
            }
        }

        setHasOptionsMenu(true)
    }

    private fun getFingerprints(): List<ComponentFingerprint<*, *>> {
        return listOf(
            FolderFingerprint { folder -> viewModel.onSubFolderSelected(folder) },
            TaskFingerprint(
                onTaskClicked = { task -> viewModel.onTaskSelected(task) },
                onCheckBoxClicked = { task, isChecked ->
                    viewModel.onTaskCheckChanged(task, isChecked)
                }
            ),
        )
    }

    private fun setUpRecyclerView(
        componentAdapter: ComponentAdapter,
        binding: FragmentTasksBinding
    ) {
        binding.recyclerviewFragmenttasksTasks.apply {
            adapter = componentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            //setHasFixedSize(true)

            addItemDecoration(HorizontalItemDecoration(24))
            addItemDecoration(VerticalItemDecoration(8))

            ItemTouchHelper(SwipingSimpleCallback { positionOfSwipedComponent: Int ->
                viewModel.onComponentSwiped(positionOfSwipedComponent)
            }).attachToRecyclerView(this)
        }
    }

    private fun setUpListeners(binding: FragmentTasksBinding) {
        binding.apply {
            fabFragmenttasksAddbutton.setOnClickListener {
                viewModel.onAddButtonClicked()
            }

            fabFragmenttasksAddtask.setOnClickListener {
                viewModel.onAddNewTaskClicked()
            }

            fabFragmenttasksAddfolder.setOnClickListener {
                viewModel.onAddNewFolderClicked()
            }
        }
    }

    private fun setUpFragmentResultListeners() {
        setFragmentResultListener("add_edit_request") { s: String, bundle: Bundle ->
            val result = bundle.getInt("add_edit_result")
            //TODO: viewModel.onAddEditResult(result)
        }

        setFragmentResultListener("add_edit_folder_request") { s: String, bundle: Bundle ->
            val result = bundle.getInt("add_edit_folder_result")
            //TODO: viewModel.onAddEditFolderResult(result)
        }

        setFragmentResultListener("folder_to_change_request") { s: String, bundle: Bundle ->
            val result = bundle.getParcelable<FolderUiState>("folder_to_change_result")
            //TODO: viewModel.onQuickFolderChangeResult(result)
        }
    }

    private fun collectEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.tasksEvent.collect { event ->
                when (event) {
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToAddTaskScreen -> {
                        /*val action =
                            TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                                TODO()
                                title = "New Task",
                                folderId = viewModel.currentFolder.value?.id ?: 1L
                            )
                        findNavController().navigate(action)*/
                    }
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToAddFolderScreen -> {
                        /*val action = TasksFragmentDirections.actionGlobalAddEditFolderDialogFragment(
                            viewModel.currentFolder.value!!
                        )
                        findNavController().navigate(action)*/
                    }
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToEditTaskScreen -> {
                        /*val action =
                            TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                                event.task,
                                "Edit Task",
                                viewModel.currentFolder.value?.id ?: 1L
                            )
                        findNavController().navigate(action)*/
                    }
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToEditFolderScreen -> {
                        /*val action = TasksFragmentDirections.actionGlobalAddEditFolderDialogFragment(
                            event.parentFolder, viewModel.currentFolder.value!!
                        )
                        findNavController().navigate(action)*/
                    }
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToDeleteAllCompletedScreen -> {
                        val action =
                            TasksFragmentDirections.actionGlobalDeleteAllCompletedDialogFragment()
                        findNavController().navigate(action)
                    }
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToDeleteFolderScreen -> {
                        val action =
                            TasksFragmentDirections.actionGlobalDeleteFolderDialogFragment(event.folder)
                        findNavController().navigate(action)
                    }
                    is TasksViewModel.TasksEvent.NavigationEvent.NavigateToQuickFolderChange -> {
                        val action = TasksFragmentDirections.actionGlobalQuickFolderChangeDialogFragment(
                            event.pinnedFolders.toTypedArray()
                        )
                        findNavController().navigate(action)
                    }
                    is TasksViewModel.TasksEvent.MessageEvent.ShowUndoDeleteTaskMessage -> {
                        Snackbar.make(requireView(), "Task deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO") {
                                viewModel.onUndoDeleteClicked(
                                    event.task,
                                    event.parentFolder
                                )
                            }
                            .show()
                    }
                    is TasksViewModel.TasksEvent.MessageEvent.ShowTaskSavedConfirmationMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is TasksViewModel.TasksEvent.MessageEvent.ShowFolderSavedConfirmationMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    /*is TasksViewModel.TasksEvent.NotifyAdapterItemChanged -> {
                        taskAdapter.notifyItemChanged(event.position)
                    }*/
                }.exhaustive
            }
        }
    }

    private fun enableBackButton(enable: Boolean) {
        if (enable) {
            (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //onBackPressedCallback.isEnabled = true
        } else {
            (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            //onBackPressedCallback.isEnabled = false
        }
    }

    /*private fun setVisibility(clicked: Boolean) {
        if (clicked) {
            fab_fragmenttasks_addtask.visibility = View.VISIBLE
            fab_fragmenttasks_addfolder.visibility = View.VISIBLE
        } else {
            fab_fragmenttasks_addtask.visibility = View.INVISIBLE
            fab_fragmenttasks_addfolder.visibility = View.INVISIBLE
        }
    }*/

    /*private fun setAnimation(clicked: Boolean) {
        if (clicked) {
            fab_fragmenttasks_addtask.startAnimation(fromBottomAnim)
            fab_fragmenttasks_addfolder.startAnimation(fromBottomAnim)
        } else {
            fab_fragmenttasks_addtask.startAnimation(toBottomAnim)
            fab_fragmenttasks_addfolder.startAnimation(toBottomAnim)
        }
    }*/

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_tasks, menu)

        /*val searchItem = menu.findItem(R.id.action_menuFragmentTasks_search)
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
        }*/
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        val editFolderItem = menu.findItem(R.id.action_menuFragmentTasks_editFolder)
        editFolderItem.isVisible = !viewModel.isCurrentFolderRoot()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_menuFragmentTasks_sortByName -> {
                //viewModel.onSortOrderSelected(SortOrder.BY_NAME)
                true
            }
            R.id.action_menuFragmentTasks_sortByDateCreated -> {
                //viewModel.onSortOrderSelected(SortOrder.BY_DATE)
                true
            }
            R.id.action_menuFragmentTasks_hideCompletedTasks -> {
                item.isChecked = !item.isChecked
                //viewModel.onHideCompletedSelected(item.isChecked)
                true
            }
            R.id.action_menuFragmentTasks_deleteAllCompletedTasks -> {
                //viewModel.onDeleteAllCompletedClicked()
                true
            }
            R.id.action_menuFragmentTasks_quickFolderChange -> {
                //viewModel.onQuickFolderChangeClicked()
                true
            }
            R.id.action_menuFragmentTasks_editFolder -> {
                //viewModel.onEditFolderClicked()
                true
            }
            android.R.id.home -> {
                //viewModel.onHomeButtonSelected()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
        findNavController().removeOnDestinationChangedListener(onDestinationChangedListener)
        if (viewModel.onAddButtonClicked.value == TasksViewModel.FABAnimation.HIDE_FABS) {
            viewModel.onAddButtonClicked.value = TasksViewModel.FABAnimation.DO_NOTHING
        }
    }*/

}
