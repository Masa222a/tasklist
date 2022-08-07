package com.android.example.tasklist

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.tasklist.databinding.FragmentTaskListBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskListFragment : Fragment() {
    private var taskList: MutableList<Task> = mutableListOf()
    lateinit var adapter: TaskListAdapter
    lateinit var binding: FragmentTaskListBinding
    private var num = 0
    private val mstColorList: List<MstColor> = listOf(
        MstColor("3", "red", 231, 87, 53),
        MstColor("4", "gray", 170, 170, 170),
    )

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

        binding.fab.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                val fragment = AddTaskFragment()
                replace(R.id.container, fragment)
                addToBackStack(null)
                commit()
            }
        }

        adapter.setOnItemClickListener(object:TaskListAdapter.OnItemClickListener{
            override fun onItemClickListener(view: View, position: Int, clickedText: Task) {
                val button = view.findViewById<ImageButton>(R.id.add_Favorite)

                val color = mstColorList[num]
                if (num + 1 == mstColorList.size) {
                    //お気に入り削除
                    num = 0
                    Log.d("0$num","$num")

                } else {
                    //お気に入り追加
                    num += 1
                    Log.d("+1$num","$num")
                }

                button.setColorFilter(Color.rgb(color.r_code, color.g_code, color.b_code), PorterDuff.Mode.SRC_ATOP)
                Toast.makeText(requireActivity(), "${clickedText}がタップされました", Toast.LENGTH_LONG).show()

            }
        })

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

    private fun getTaskList() {
        val pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = pref.getString("taskList", null)
        if (json != null) {
            val listType = object : TypeToken<MutableList<Task>>() {}.type
            val taskList = gson.fromJson<MutableList<Task>>(json, listType)
            for (task in taskList) {
                if (this.taskList.contains(task)) {
                    Log.d("task", "taskが含まれています。")
                } else {
                    this.taskList += task
                }
            }
        }

    }

}