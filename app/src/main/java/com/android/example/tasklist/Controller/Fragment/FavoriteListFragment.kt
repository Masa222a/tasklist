package com.android.example.tasklist.Controller.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.tasklist.Model.Task
import com.android.example.tasklist.SharedPreferencesManager
import com.android.example.tasklist.View.FavoriteListAdapter
import com.android.example.tasklist.databinding.FragmentFavoriteListBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoriteListFragment : Fragment() {
    lateinit var binding: FragmentFavoriteListBinding
    private var taskList: MutableList<Task> = mutableListOf()
    lateinit var adapter: FavoriteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        val recyclerView = binding.recyclerView
        adapter = FavoriteListAdapter(taskList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        return view
    }

    private fun changeFavoriteList(taskList: MutableList<Task>) {
        adapter.updateTaskList(taskList)
    }

    fun getCurrentTaskList() {
        val json = SharedPreferencesManager.instance.getCurrentTaskList(requireActivity(), "pref")
        val gson = Gson()
        if (json != null) {
            val listType = object : TypeToken<MutableList<Task>>() {}.type
            val taskList = gson.fromJson<MutableList<Task>>(json, listType)
            this.taskList = taskList
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.title = "お気に入り"
        getCurrentTaskList()
        changeFavoriteList(taskList)
    }

}