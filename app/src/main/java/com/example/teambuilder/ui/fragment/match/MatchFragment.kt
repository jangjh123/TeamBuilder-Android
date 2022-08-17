package com.example.teambuilder.ui.fragment.match

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.teambuilder.R
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.data.model.Team
import com.example.teambuilder.databinding.FragmentMatchBinding
import com.example.teambuilder.ui.BaseFragment
import com.example.teambuilder.ui.component.adapter.TeamAdapter
import com.example.teambuilder.ui.component.dialog.DefaultDialog
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchFragment : BaseFragment<FragmentMatchBinding>(R.layout.fragment_match) {
    private val args: MatchFragmentArgs by navArgs()
    private val viewModel: MatchViewModel by viewModels()
    private lateinit var teamAAdapter: TeamAdapter
    private lateinit var teamBAdapter: TeamAdapter
    private var isLoaded = false
    private var isPlus = false
    private var isTeamAScoreChange = false
    private val fadeIn1: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fade_in_6
        )
    }
    private val fadeIn2: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fade_in_7
        )
    }
    private val fadeOut1: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fade_out_2
        )
    }
    private val fadeOut2: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fade_out_3
        )
    }
    private lateinit var scoreSnackBar: Snackbar

    override fun proceed() {
        initAdapter()
        binding.fragment = this@MatchFragment
        binding.teamAAdapter = teamAAdapter
        binding.teamBAdapter = teamBAdapter
        setCurrentMatch()
    }

    private fun initAdapter() {
        teamAAdapter = TeamAdapter(false, Team.TEAM_A, onClickPlayer = {
            showScoreDialog(it.name)
        })
        teamBAdapter = TeamAdapter(false, Team.TEAM_B, onClickPlayer = {
            showScoreDialog(it.name)
        })
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

    private fun showScoreDialog(name: String) {
        DefaultDialog(
            if (isPlus) {
                "득점자"
            } else {
                "득점 취소"
            },
            if (isPlus) {
                "$name 선수가 득점했나요?"
            } else {
                "$name 선수의 득점을 취소합니다."
            },
            "아니요",
            "네",
            null,
            null,
            onClickConfirm = {
                scoreSnackBar.dismiss()
                if (isTeamAScoreChange) {
                    binding.rvTeamB.startAnimation(fadeIn2)
                    teamAAdapter.isTouchable = false
                    viewModel.setTeamAScore(isPlus)
                } else {
                    binding.rvTeamA.startAnimation(fadeIn1)
                    teamBAdapter.isTouchable = false
                    viewModel.setTeamBScore(isPlus)
                }
                viewModel.setPersonalScore(name, isPlus)
            }
        ).show(childFragmentManager, "score")
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
        isPlus = true
        isTeamAScoreChange = true
        binding.rvTeamB.startAnimation(fadeOut1)
        showScoreSnackBar()
        teamAAdapter.isTouchable = true
        teamBAdapter.isTouchable = false
    }

    fun onClickBUp(view: View) {
        isPlus = true
        isTeamAScoreChange = false
        binding.rvTeamA.startAnimation(fadeOut2)
        showScoreSnackBar()
        teamBAdapter.isTouchable = true
        teamAAdapter.isTouchable = false
    }

    fun onClickADown(view: View) {
        if (viewModel.teamAScore.value == 0) {
            showSnackBar("현재 0점 입니다.")
        } else {
            isPlus = false
            teamAAdapter.isTouchable = true
            teamBAdapter.isTouchable = false
            isTeamAScoreChange = true
            binding.rvTeamB.startAnimation(fadeOut1)
            showScoreSnackBar()
        }
    }

    fun onClickBDown(view: View) {
        if (viewModel.teamBScore.value == 0) {
            showSnackBar("현재 0점 입니다.")
        } else {
            isPlus = false
            teamBAdapter.isTouchable = true
            teamAAdapter.isTouchable = false
            isTeamAScoreChange = false
            binding.rvTeamA.startAnimation(fadeOut2)
            showScoreSnackBar()
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

    private fun showScoreSnackBar() {
        scoreSnackBar = Snackbar.make(
            binding.root,
            if (isPlus) {
                "득점한 선수를 선택해주세요."
            } else {
                "득점을 취소할 선수를 선택해주세요."
            }, Snackbar.LENGTH_INDEFINITE
        )
        scoreSnackBar.show()
    }
}