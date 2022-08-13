package com.android.example.tasklist.Controller.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.example.tasklist.Model.Task
import com.android.example.tasklist.View.BottomNavigationPagerAdapter
import com.android.example.tasklist.R
import com.android.example.tasklist.View.TaskListAdapter
import com.android.example.tasklist.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.viewPager.adapter = BottomNavigationPagerAdapter(this)
        binding.viewPager.isUserInputEnabled = false

        binding.bottomNavigation.setOnItemSelectedListener {
            val currentItem = getCurrentItem(it.itemId)
            binding.viewPager.setCurrentItem(currentItem, true)
            return@setOnItemSelectedListener true
        }

        return binding.root
    }

    private fun getCurrentItem(itemId: Int): Int {
        return when (itemId) {
            R.id.task_List -> 0
            R.id.favolite_List -> 1
            else -> 0
        }
    }

}