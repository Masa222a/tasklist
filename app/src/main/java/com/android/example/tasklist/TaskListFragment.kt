package com.android.example.tasklist

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.tasklist.databinding.FragmentTaskListBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskListFragment : Fragment() {
    private var taskList: ArrayList<Task> = arrayListOf()
    lateinit var adapter: TaskListAdapter
    lateinit var binding: FragmentTaskListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        val pref = PreferenceManager.getDefaultSharedPreferences(requireActivity())
        pref.edit{
            clear()
        }

        val recyclerView = binding.recyclerView
        adapter = TaskListAdapter(taskList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        activity?.title = "タスク一覧"
        getTaskList()
        adapter.updateTaskList(taskList)
        adapter.notifyDataSetChanged()
    }

    fun getTaskList() {
        val pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = pref.getString("taskList", null)
        val listType = object : TypeToken<ArrayList<Task>>() {}.type
        val taskList = gson.fromJson<ArrayList<Task>>(json, listType)
        if (json != null) {
            this.taskList = taskList
        }

    }

    private fun createDummyTaskList(): MutableList<Task> {
        var taskList: MutableList<Task> = mutableListOf()
        var task = Task("task",  "2020/11/24", true)

        // 20件のサンプルーデータを登録
        var i = 0
        while (i < 20) {
            i++
            taskList.add(task)
        }
        return taskList
    }
}