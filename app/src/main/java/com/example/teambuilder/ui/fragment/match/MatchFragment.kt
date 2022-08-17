package com.example.teambuilder.ui.fragment.match

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.teambuilder.R
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.data.model.Team
import com.example.teambuilder.databinding.FragmentMatchBinding
import com.example.teambuilder.ui.BaseFragment
import com.example.teambuilder.ui.component.adapter.TeamAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchFragment : BaseFragment<FragmentMatchBinding>(R.layout.fragment_match) {
    private val args: MatchFragmentArgs by navArgs()
    private val viewModel: MatchViewModel by viewModels()
    private val teamAAdapter = TeamAdapter(Team.TEAM_A)
    private val teamBAdapter = TeamAdapter(Team.TEAM_B)

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
            }
        } else {
            teamAAdapter.submitList(args.teamA?.toList())
            teamBAdapter.submitList(args.teamB?.toList())
        }
        BottomSheetBehavior.from(binding.bs).state = BottomSheetBehavior.STATE_COLLAPSED
    }
}