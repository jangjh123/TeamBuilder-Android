package com.example.teambuilder.ui.fragment.statistics

import com.example.teambuilder.R
import com.example.teambuilder.databinding.FragmentStatisticsBinding
import com.example.teambuilder.ui.BaseFragment
import com.example.teambuilder.ui.component.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(R.layout.fragment_statistics) {
    override fun proceed() {
        initViewPager()
    }

    private fun initViewPager() {
        binding.vp.adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(binding.layTab, binding.vp) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "매치"
                }
                1-> {
                    tab.text = "랭킹"
                }
            }
        }.attach()
    }
}