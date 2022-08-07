package com.android.example.tasklist.View

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.example.tasklist.Controller.Fragment.MainFragment
import com.android.example.tasklist.Controller.Fragment.TaskListFragment
import com.android.example.tasklist.Controller.Fragment.FavoriteListFragment

class BottomNavigationPagerAdapter(fm: MainFragment) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                TaskListFragment()
            }
            1 -> {
                FavoriteListFragment()
            }
            else -> {
                TaskListFragment()
            }
        }
    }

}
