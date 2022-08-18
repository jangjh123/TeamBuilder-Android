package com.example.teambuilder.ui.fragment.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.teambuilder.R
import com.example.teambuilder.databinding.FragmentHomeBinding
import com.example.teambuilder.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()

    override fun proceed() {
        binding.fragment = this@HomeFragment
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        with(binding) {
            viewModel.getMatchExist {
                if (it) {
                    tvScoreOrNoMatch.text = "현재 스코어"
                    tvScore.visibility = View.VISIBLE
                    tvScore.text // 스코어 표시

                    btnCurrentMatch.setOnClickListener {
                        findNavController().navigate(R.id.action_frag_home_to_frag_match)
                    }
                    btnBuildTeam.alpha = 0.4f
                } else {
                    tvScoreOrNoMatch.text = "진행중인 매치가 없습니다."
                    tvScore.visibility = View.GONE

                    btnBuildTeam.setOnClickListener {
                        findNavController().navigate(R.id.action_frag_home_to_frag_team_build)
                    }
                    btnCurrentMatch.alpha = 0.4f
                }
            }

            btnStat.setOnClickListener {
                findNavController().navigate(R.id.action_frag_home_to_frag_stat)
            }

            btnAddPlayer.setOnClickListener {
                // 다이얼로그
            }
        }
    }
}