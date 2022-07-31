package com.example.teambuilder.ui.fragment.team_build

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import androidx.fragment.app.viewModels
import com.example.teambuilder.R
import com.example.teambuilder.databinding.FragmentTeamBuildBinding
import com.example.teambuilder.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TeamBuildFragment : BaseFragment<FragmentTeamBuildBinding>(R.layout.fragment_team_build) {
    private val viewModel: TeamBuildViewModel by viewModels()

    override fun proceed() {
        binding.fragment = this@TeamBuildFragment
        startAnim()
    }

    private fun startAnim() {
        val fadeIn1 = AlphaAnimation(0f, 1f).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 1000
        }

        val fadeIn2 = AlphaAnimation(0f, 0.4f).apply {
            interpolator = AccelerateDecelerateInterpolator()
            fillAfter = true
            startOffset = 1000
            duration = 1500
        }

        binding.textView3.startAnimation(fadeIn1)
        binding.btnTeamALeader.startAnimation(fadeIn1)
        binding.btnTeamBLeader.startAnimation(fadeIn1)

        binding.textView4.startAnimation(fadeIn2)
        binding.rgMemberSelectWay.startAnimation(fadeIn2)
    }

    fun setTeamALeader(view: View) {

    }

    fun setTeamBLeader(view: View) {

    }

    override fun setObserver() {
        viewModel.players.observe(viewLifecycleOwner) {
            it.forEach { player ->

            }
        }
    }
}