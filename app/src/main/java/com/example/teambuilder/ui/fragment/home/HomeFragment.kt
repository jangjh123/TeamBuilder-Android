package com.example.teambuilder.ui.fragment.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.teambuilder.R
import com.example.teambuilder.databinding.FragmentHomeBinding
import com.example.teambuilder.ui.BaseFragment
import com.example.teambuilder.ui.component.dialog.AddPlayerDialog
import com.example.teambuilder.ui.component.dialog.TeamNumberDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()

    override fun proceed() {
        binding.fragment = this@HomeFragment
        initView()
    }

    private fun initView() {
        with(binding) {
            viewModel.getMatchExist {
                if (it) {
                    tvScoreOrNoMatch.text = "현재 스코어"
                    tvScore.visibility = View.VISIBLE

                    viewModel.getTeamAScore { teamAScore ->
                        viewModel.getTeamBScore { teamBScore ->
                            tvScore.text = "$teamAScore : $teamBScore"
                        }
                    }

                    btnCurrentMatch.setOnClickListener {
                        findNavController().navigate(R.id.action_frag_home_to_frag_match)
                    }
                    btnBuildTeam.alpha = 0.4f
                } else {
                    tvScoreOrNoMatch.text = "진행중인 매치가 없습니다."
                    tvScore.visibility = View.GONE

                    btnBuildTeam.setOnClickListener {
                        TeamNumberDialog(
                            onClickTwoTeam = {
                                findNavController().navigate(HomeFragmentDirections.actionFragHomeToFragTeamBuild())
                            },
                            onClickThreeTeam = {
                                findNavController().navigate(HomeFragmentDirections.actionFragHomeToFragMultipleTeamBuild())
                            }
                        ).show(childFragmentManager, "team_number")
                    }
                    btnCurrentMatch.alpha = 0.4f
                }
            }

            btnStatistics.setOnClickListener {
                findNavController().navigate(R.id.action_frag_home_to_frag_statistics)
            }

            btnAddPlayer.setOnClickListener {
                AddPlayerDialog {
                    viewModel.enrollPlayer(it)
                    showSnackBar("${it.first} 선수가 추가되었습니다.")
                }.show(childFragmentManager, "add_player")
            }
        }
    }
}