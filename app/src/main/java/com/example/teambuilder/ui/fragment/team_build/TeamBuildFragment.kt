package com.example.teambuilder.ui.fragment.team_build

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.teambuilder.R
import com.example.teambuilder.databinding.FragmentTeamBuildBinding
import com.example.teambuilder.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamBuildFragment : BaseFragment<FragmentTeamBuildBinding>(R.layout.fragment_team_build) {
    private val viewModel: TeamBuildViewModel by viewModels()

    override fun proceed() {
        viewModel.foo()
    }

    override fun setObserver() {
        viewModel.players.observe(viewLifecycleOwner) {
            it.forEach { player ->

            }
        }
    }
}