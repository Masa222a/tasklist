package com.android.example.tasklist.Controller.Fragment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.tasklist.Model.Task
import com.android.example.tasklist.R
import com.android.example.tasklist.SharedPreferencesManager
import com.android.example.tasklist.View.TaskListAdapter
import com.android.example.tasklist.databinding.FragmentTaskListBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskListFragment : Fragment(), AddTaskFragment.AddTaskListener{
    private var taskList: MutableList<Task> = mutableListOf()
    lateinit var adapter: TaskListAdapter
    lateinit var binding: FragmentTaskListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        val recyclerView = binding.recyclerView
        adapter = TaskListAdapter(taskList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        binding.fab.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                val fragment = AddTaskFragment()
                fragment.setOnAddTaskListener(object : AddTaskFragment.AddTaskListener {
                    override fun finishAddTask() {
                        activity?.title = "タスク一覧"
                        getTaskList()
                        adapter.updateTaskList(taskList)
                    }
                })
                replace(R.id.container, fragment)
                addToBackStack(null)
                commit()
            }
        }

        adapter.setOnItemClickListener(object: TaskListAdapter.OnItemClickListener {
            override fun onItemClickListener(view: View, position: Int, clickedText: Task) {
                taskList[position].isFavorite = !taskList[position].isFavorite
                SharedPreferencesManager.instance.saveTaskList(requireActivity(), "pref", taskList)

                Toast.makeText(requireActivity(), "${clickedText}", Toast.LENGTH_SHORT).show()

                adapter.updateTaskList(taskList)
            }
        })

        val swipeToDismissTouchHelper = getSwipeToDismissTouchHelper(adapter)
        swipeToDismissTouchHelper.attachToRecyclerView(recyclerView)

        return view
    }

    override fun onResume() {
        super.onResume()
        finishAddTask()
    }

    fun getTaskList() {
        val json = SharedPreferencesManager.instance.getCurrentTaskList(requireActivity(), "pref")
        val gson = Gson()
        if (json != null) {
            val listType = object : TypeToken<MutableList<Task>>() {}.type
            val taskList = gson.fromJson<MutableList<Task>>(json, listType)
            this.taskList = taskList
        }

    }

    override fun finishAddTask() {
        activity?.title = "タスク一覧"
        getTaskList()
        adapter.updateTaskList(taskList)
    }


    private fun getSwipeToDismissTouchHelper(adapter: RecyclerView.Adapter<TaskListAdapter.ViewHolder>) =
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
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
                taskList.removeAt(viewHolder.absoluteAdapterPosition)
                adapter.notifyItemRemoved(viewHolder.absoluteAdapterPosition)
                SharedPreferencesManager.instance.saveTaskList(requireActivity(), "pref", taskList)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                val itemView = viewHolder.itemView
                val background = ColorDrawable()
                background.color = Color.parseColor("#f44336")
                if (dX < 0)
                    background.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                else
                    background.setBounds(
                        itemView.left,
                        itemView.top,
                        itemView.left + dX.toInt(),
                        itemView.bottom
                    )

                background.draw(c)
            }
        })
}