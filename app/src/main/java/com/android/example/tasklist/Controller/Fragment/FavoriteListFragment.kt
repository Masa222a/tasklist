package com.android.example.tasklist.Controller.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.tasklist.Model.Task
import com.android.example.tasklist.View.FavoriteListAdapter
import com.android.example.tasklist.databinding.FragmentFavoriteListBinding

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

    override fun onResume() {
        super.onResume()
        activity?.title = "お気に入り"
    }

}