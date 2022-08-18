package com.example.teambuilder.ui.component

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.teambuilder.ui.fragment.match_history.MatchHistoryFragment
import com.example.teambuilder.ui.fragment.ranking.RankingFragment

private const val TAB_COUNT = 2

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MatchHistoryFragment()
            else -> RankingFragment()
        }
    }
}