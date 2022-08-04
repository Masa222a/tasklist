package com.android.example.tasklist

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

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
