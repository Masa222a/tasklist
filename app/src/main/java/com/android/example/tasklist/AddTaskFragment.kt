package com.android.example.tasklist

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.example.tasklist.databinding.FragmentAddTaskBinding
import com.google.gson.Gson

class AddTaskFragment : Fragment() {
    lateinit var binding: FragmentAddTaskBinding

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
                val taskList = arrayListOf<Task>()
                taskList.add(Task(binding.editTitle.text.toString(), binding.editDate.text.toString(), frag = false))
                val pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
                val shardPrefEditor = pref.edit()
                val gson = Gson()
                shardPrefEditor.putString("taskList", gson.toJson(taskList))
                shardPrefEditor.apply()
                Log.d("Gson", gson.toJson(taskList))

                binding.editTitle.text.clear()
                binding.editDate.text.clear()
            }
        }

        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

}