package com.android.example.tasklist.Controller.Fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.example.tasklist.Model.Task
import com.android.example.tasklist.SharedPreferencesManager
import com.android.example.tasklist.databinding.FragmentAddTaskBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddTaskFragment : Fragment() {
    lateinit var binding: FragmentAddTaskBinding
    var listener: AddTaskListener? = null

    interface AddTaskListener {
        fun finishAddTask()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(layoutInflater, container, false)

        binding.registerButton.setOnClickListener {
            if (binding.editTitle.text.isEmpty()) {
                Toast.makeText(activity, "タイトルを入力してください", Toast.LENGTH_SHORT).show()
            } else if (binding.editDate.text.isEmpty()){
                Toast.makeText(activity, "日時を入力してください", Toast.LENGTH_SHORT).show()
            } else {

                val newTask = Task(
                    binding.editTitle.text.toString(),
                    binding.editDate.text.toString(),
                    isFavorite = false
                )
                var taskList = mutableListOf<Task>()

                val json = SharedPreferencesManager.instance.getCurrentTaskList(requireActivity(), "pref")
                val gson = Gson()

                if (json != null) {
                    val listType = object : TypeToken<MutableList<Task>>() {}.type
                    val currentTaskList = gson.fromJson<MutableList<Task>>(json, listType)
                    taskList = currentTaskList
                }

                if (!taskList.contains(newTask)) {
                    taskList.add(newTask)
                    SharedPreferencesManager.instance.saveTaskList(requireActivity(), "pref", taskList)
                }
                binding.editTitle.text.clear()
                binding.editDate.text.clear()
            }
        }

        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
            listener?.finishAddTask()
        }

        return binding.root
    }

    fun setOnAddTaskListener(listener: AddTaskListener) {
        this.listener = listener
    }

}