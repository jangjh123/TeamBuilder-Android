package com.example.teambuilder.ui.fragment.match

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.teambuilder.R
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.data.model.Team
import com.example.teambuilder.databinding.FragmentMatchBinding
import com.example.teambuilder.ui.BaseFragment
import com.example.teambuilder.ui.component.adapter.TeamAdapter
import com.example.teambuilder.ui.component.dialog.DefaultDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchFragment : BaseFragment<FragmentMatchBinding>(R.layout.fragment_match) {
    private val args: MatchFragmentArgs by navArgs()
    private val viewModel: MatchViewModel by viewModels()
    private val teamAAdapter = TeamAdapter(Team.TEAM_A)
    private val teamBAdapter = TeamAdapter(Team.TEAM_B)
    private var isLoaded = false

    override fun proceed() {
        binding.fragment = this@MatchFragment
        binding.teamAAdapter = teamAAdapter
        binding.teamBAdapter = teamBAdapter
        setCurrentMatch()
    }

    private fun setCurrentMatch() {
        if (args.teamA == null) {
            viewModel.getCurrentMatch {
                val gson = Gson()
                teamAAdapter.submitList(
                    gson.fromJson(it.teamAPlayers, Array<Player>::class.java).toList()
                )
                teamBAdapter.submitList(
                    gson.fromJson(it.teamBPlayers, Array<Player>::class.java).toList()
                )
                isLoaded = true
            }
        } else {
            teamAAdapter.submitList(args.teamA?.toList())
            teamBAdapter.submitList(args.teamB?.toList())
        }
    }

    override fun setObserver() {
        viewModel.teamAScore.onChanged {
            binding.tvAScore.text = "$it"
        }

        viewModel.teamBScore.onChanged {
            binding.tvBScore.text = "$it"
        }
    }

    fun onClickAUp(view: View) {
        viewModel.setTeamAScore(1)
    }

    fun onClickADown(view: View) {
        if (viewModel.teamAScore.value == 0) {
            showSnackBar("현재 0점 입니다.")
        } else {
            viewModel.setTeamAScore(-1)
        }
    }

    fun onClickBUp(view: View) {
        viewModel.setTeamBScore(1)
    }

    fun onClickBDown(view: View) {
        if (viewModel.teamBScore.value == 0) {
            showSnackBar("현재 0점 입니다.")
        } else {
            viewModel.setTeamBScore(-1)
        }
    }

    fun onClickQuitMatch(view: View) {
        DefaultDialog(
            "경기 종료",
            "경기를 종료합니다.",
            "취소",
            "확인",
            null,
            null,
            onClickConfirm = {
                if (isLoaded) {
                    viewModel.quitMatch(
                        true,
                        null,
                        null
                    )
                } else {
                    viewModel.quitMatch(
                        isLoaded,
                        args.teamA,
                        args.teamB
                    )
                }
            }
        ).show(childFragmentManager, "quit_match")
    }
}