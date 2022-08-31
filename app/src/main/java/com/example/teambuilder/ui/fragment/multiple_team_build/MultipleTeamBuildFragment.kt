package com.example.teambuilder.ui.fragment.multiple_team_build

import android.view.View
import androidx.fragment.app.viewModels
import com.example.teambuilder.R
import com.example.teambuilder.databinding.FragmentMultipleTeamBuildBinding
import com.example.teambuilder.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MultipleTeamBuildFragment :
    BaseFragment<FragmentMultipleTeamBuildBinding>(R.layout.fragment_multiple_team_build) {
    private val viewModel: MultipleTeamBuildViewModel by viewModels()

    override fun proceed() {
        binding.fragment = this@MultipleTeamBuildFragment
    }

    fun onClickALeader(view: View) {

    }

    fun onClickBLeader(view: View) {

    }

    fun onClickCLeader(view: View) {

    }
}