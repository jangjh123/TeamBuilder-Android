package com.example.teambuilder.ui.fragment.team_build

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.teambuilder.R
import com.example.teambuilder.databinding.FragmentTeamBuildBinding
import com.example.teambuilder.ui.BaseFragment
import com.example.teambuilder.ui.component.ChoiceDialog
import com.example.teambuilder.ui.component.MemberPickerFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TeamBuildFragment : BaseFragment<FragmentTeamBuildBinding>(R.layout.fragment_team_build) {
    private val viewModel: TeamBuildViewModel by viewModels()
    private var isSetALeader = false
    private var isSetBLeader = false
    private var isSelectWayVisible = false
    private var isRandom = false
    private var isDirect = false
    private var isMemberCountVisible = false
    private var isSix = false
    private var isSeven = false
    private var isSetTeamMembersVisible = false

    private val fadeIn1 = AlphaAnimation(0f, 1f).apply {
        interpolator = AccelerateDecelerateInterpolator()
        duration = 1000
    }

    private val fadeIn2 = AlphaAnimation(0f, 0.1f).apply {
        interpolator = AccelerateDecelerateInterpolator()
        fillAfter = true
        startOffset = 1000
        duration = 1000
    }

    private val fadeIn3 = AlphaAnimation(0.1f, 1f).apply {
        interpolator = AccelerateDecelerateInterpolator()
        fillAfter = true
        duration = 1000
    }

    private val fadeIn4 = AlphaAnimation(0.1f, 1f).apply {
        interpolator = AccelerateDecelerateInterpolator()
        fillAfter = true
        duration = 1000
    }

    private val fadeIn5 = AlphaAnimation(0.1f, 1f).apply {
        interpolator = AccelerateDecelerateInterpolator()
        fillAfter = true
        duration = 1000
    }

    override fun proceed() {
        binding.fragment = this@TeamBuildFragment
        startAnim()
        viewModel.getPlayers()
    }

    private fun startAnim() {
        binding.textView3.startAnimation(fadeIn1)
        binding.btnTeamALeader.startAnimation(fadeIn1)
        binding.btnTeamBLeader.startAnimation(fadeIn1)

        binding.textView4.startAnimation(fadeIn2)
        binding.rgMemberSelectWay.startAnimation(fadeIn2)
        binding.textView2.startAnimation(fadeIn2)
        binding.rgNVsN.startAnimation(fadeIn2)
        binding.btnSelectTeamMembers.startAnimation(fadeIn2)
    }

    fun selectALeader(view: View) {
        if (!viewModel.players.value.isNullOrEmpty()) {
            ChoiceDialog(viewModel.players.value!!, "A팀 리더 선택") {
                if (it.isChosen) {
                    Snackbar.make(binding.root, "이미 선택된 선수입니다.", Snackbar.LENGTH_SHORT).show()
                } else {
                    binding.tvALeader.apply {
                        text = it.name
                        setTextColor(getColor(R.color.point_color))
                        if (viewModel.teamALeader == null) {
                            viewModel.setALeader(it)
                        } else {
                            viewModel.teamALeader!!.isChosen = false
                            viewModel.setALeader(it)
                        }
                    }
                    it.isChosen = true
                    isSetALeader = true
                    checkLeaderSettings()
                }
            }.show(childFragmentManager, "set_a_leader")
        }
    }

    fun selectBLeader(view: View) {
        if (!viewModel.players.value.isNullOrEmpty()) {
            ChoiceDialog(viewModel.players.value!!, "B팀 리더 선택") {
                if (it.isChosen) {
                    Snackbar.make(binding.root, "이미 선택된 선수입니다.", Snackbar.LENGTH_SHORT).show()
                } else {
                    binding.tvBLeader.apply {
                        text = it.name
                        setTextColor(getColor(R.color.point_color))
                        if (viewModel.teamBLeader == null) {
                            viewModel.setBLeader(it)
                        } else {
                            viewModel.teamBLeader!!.isChosen = false
                            viewModel.setBLeader(it)
                        }
                    }
                    it.isChosen = true
                    isSetBLeader = true
                    checkLeaderSettings()
                }
            }.show(childFragmentManager, "set_b_leader")
        }
    }

    private fun checkLeaderSettings() {
        if (isSetALeader && isSetBLeader && !isSelectWayVisible) {
            binding.textView4.startAnimation(fadeIn3)
            binding.rgMemberSelectWay.startAnimation(fadeIn3)
            isSelectWayVisible = true
        }
    }

    private fun setColorBySelection(view1: View, view2: View, chosen: Boolean, unChosen: Boolean) {
        if (!chosen) {
            playColorAnimation(view1, getColor(R.color.white), getColor(R.color.point_color))
            (view1 as TextView).setTextColor(getColor(R.color.white))
        }

        if (unChosen) {
            playColorAnimation(view2, getColor(R.color.point_color), getColor(R.color.white))
            (view2 as TextView).setTextColor(getColor(R.color.gray))
        }
    }

    fun setRandom(view: View) {
        if (isSelectWayVisible) {
            setColorBySelection(view, binding.btnDirect, isRandom, isDirect)
            isRandom = true
            isDirect = false
            checkWaySetting()
        }
    }

    fun setDirect(view: View) {
        if (isSelectWayVisible) {
            setColorBySelection(view, binding.btnRandom, isDirect, isRandom)
            isDirect = true
            isRandom = false
            checkWaySetting()
        }
    }

    private fun checkWaySetting() {
        if (isRandom || isDirect) {
            if (!isMemberCountVisible) {
                binding.textView2.startAnimation(fadeIn4)
                binding.rgNVsN.startAnimation(fadeIn4)
                isMemberCountVisible = true
            }
        }
    }

    fun setSix(view: View) {
        if (isMemberCountVisible) {
            setColorBySelection(view, binding.btn7Vs7, isSix, isSeven)
            isSix = true
            isSeven = false
            checkMemberCountSetting()
        }
    }

    fun setSeven(view: View) {
        if (isMemberCountVisible) {
            setColorBySelection(view, binding.btn6Vs6, isSeven, isSix)
            isSeven = true
            isSix = false
            checkMemberCountSetting()
        }
    }

    private fun checkMemberCountSetting() {
        if (isSix || isSeven) {
            if (!isSetTeamMembersVisible) {
                binding.btnSelectTeamMembers.startAnimation(fadeIn5)
                isSetTeamMembersVisible = true
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setTeamMembers(view: View) {
        if (isSetTeamMembersVisible) {
            when {
                isRandom -> {

                }
                isDirect -> {
                    MemberPickerFragment().show(childFragmentManager, "member_picker")
                }
            }
        }
    }


    fun confirmTeams(view: View) {

    }
}