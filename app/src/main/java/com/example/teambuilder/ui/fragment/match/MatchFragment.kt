package com.example.teambuilder.ui.fragment.match

import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.teambuilder.R
import com.example.teambuilder.data.model.Team
import com.example.teambuilder.databinding.FragmentMatchBinding
import com.example.teambuilder.ui.BaseFragment
import com.example.teambuilder.ui.component.adapter.TeamAdapter
import com.example.teambuilder.ui.component.dialog.DefaultDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchFragment : BaseFragment<FragmentMatchBinding>(R.layout.fragment_match) {
    private val viewModel: MatchViewModel by viewModels()
    private lateinit var teamAAdapter: TeamAdapter
    private lateinit var teamBAdapter: TeamAdapter
    private var isPlus = false
    private var isTeamAScoreChange = false
    private var isOnScoring = false
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

    override fun onResume() {
        super.onResume()
        setBackButtonPrevent()
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
        viewModel.getCurrentMatch {
            teamAAdapter.submitList(
                it.first
            )
            teamBAdapter.submitList(
                it.second
            )
        }
    }

    private fun showScoreDialog(name: String) {
        DefaultDialog(
            if (isPlus) {
                "?????????"
            } else {
                "?????? ??????"
            },
            if (isPlus) {
                "$name ????????? ????????????????"
            } else {
                "$name ????????? ????????? ???????????????."
            },
            "?????????",
            "???",
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
                isOnScoring = false
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
        if (!isOnScoring) {
            isPlus = true
            isTeamAScoreChange = true
            binding.rvTeamB.startAnimation(fadeOut1)
            showScoreSnackBar()
            teamAAdapter.isTouchable = true
            teamBAdapter.isTouchable = false
            isOnScoring = true
        }
    }

    fun onClickBUp(view: View) {
        if (!isOnScoring) {
            isPlus = true
            isTeamAScoreChange = false
            binding.rvTeamA.startAnimation(fadeOut2)
            showScoreSnackBar()
            teamBAdapter.isTouchable = true
            teamAAdapter.isTouchable = false
            isOnScoring = true
        }
    }

    fun onClickADown(view: View) {
        if (!isOnScoring) {
            if (viewModel.teamAScore.value == 0) {
                showSnackBar("?????? 0??? ?????????.")
            } else {
                isPlus = false
                teamAAdapter.isTouchable = true
                teamBAdapter.isTouchable = false
                isTeamAScoreChange = true
                binding.rvTeamB.startAnimation(fadeOut1)
                showScoreSnackBar()
                isOnScoring = true
            }
        }
    }

    fun onClickBDown(view: View) {
        if (!isOnScoring) {
            if (viewModel.teamBScore.value == 0) {
                showSnackBar("?????? 0??? ?????????.")
            } else {
                isPlus = false
                teamBAdapter.isTouchable = true
                teamAAdapter.isTouchable = false
                isTeamAScoreChange = false
                binding.rvTeamA.startAnimation(fadeOut2)
                showScoreSnackBar()
                isOnScoring = true
            }
        }
    }

    fun onClickQuitMatch(view: View) {
        if (!isOnScoring) {
            DefaultDialog(
                "?????? ??????",
                "????????? ???????????????.",
                "??????",
                "??????",
                null,
                null,
                onClickConfirm = {
                    viewModel.quitMatch()
                    viewModel.resetScore()
                    findNavController().navigate(R.id.action_frag_match_to_frag_stat)
                }
            ).show(childFragmentManager, "quit_match")
        }
    }

    private fun showScoreSnackBar() {
        scoreSnackBar = Snackbar.make(
            binding.root,
            if (isPlus) {
                "????????? ????????? ??????????????????."
            } else {
                "????????? ????????? ????????? ??????????????????."
            }, Snackbar.LENGTH_INDEFINITE
        )
        scoreSnackBar.show()
    }

    private fun setBackButtonPrevent() {
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()

        requireView().setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (isOnScoring) {
                            return true
                        }
                        return false
                    }
                }
                return false
            }
        })
    }
}