package com.example.teambuilder.ui.fragment.home

import com.example.teambuilder.R
import com.example.teambuilder.databinding.FragmentHomeBinding
import com.example.teambuilder.ui.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun proceed() {
        binding.fragment = this@HomeFragment
        initView()
    }

    private fun initView() {
        with(binding) {
            btnBuildTeam.setOnClickListener {

            }

            btnCurrentMatch.setOnClickListener {

            }

            btnStat.setOnClickListener {

            }

            btnAddPlayer.setOnClickListener {
                // 다이얼로그
            }
        }
    }

    override fun setObserver() {

    }
}