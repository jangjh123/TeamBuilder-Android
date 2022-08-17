package com.example.teambuilder.ui.fragment.match

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.teambuilder.R
import com.example.teambuilder.databinding.FragmentMatchBinding
import com.example.teambuilder.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchFragment : BaseFragment<FragmentMatchBinding>(R.layout.fragment_match) {
    private val args: MatchFragmentArgs by navArgs()
    private val viewModel: MatchViewModel by viewModels()

    override fun proceed() {
        binding.fragment = this@MatchFragment

        setCurrentMatch()
    }

    private fun setCurrentMatch() {
//        if (args.teamA == null) {
//            viewModel.getCurrentMatch {
//                teamAAdapter = TeamAdapter(Team.TEAM_A, it.teamAPlayers.split("\n").toList())
//                binding.teamAAdapter = teamAAdapter
//            }
//        } else {
//            initViewFromBuild()
//        }
    }

    private fun initViewFromBuild() {
        with(binding) {

        }
    }
}