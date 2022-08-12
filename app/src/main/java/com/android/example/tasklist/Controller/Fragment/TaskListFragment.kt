package com.android.example.tasklist.Controller.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.tasklist.Model.Task
import com.android.example.tasklist.R
import com.android.example.tasklist.SharedPreferencesManager
import com.android.example.tasklist.View.TaskListAdapter
import com.android.example.tasklist.databinding.FragmentTaskListBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskListFragment : Fragment() {
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

        return view
    }

    override fun onResume() {
        super.onResume()
        activity?.title = "タスク一覧"
        getTaskList()
        adapter.updateTaskList(taskList)
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


}