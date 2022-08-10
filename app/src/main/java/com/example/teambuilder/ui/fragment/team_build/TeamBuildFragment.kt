package com.example.teambuilder.ui.fragment.team_build

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.teambuilder.R
import com.example.teambuilder.databinding.FragmentTeamBuildBinding
import com.example.teambuilder.ui.BaseFragment
import com.example.teambuilder.ui.component.dialog.ChoiceDialog
import com.example.teambuilder.ui.component.dialog.DefaultDialog
import com.example.teambuilder.ui.component.dialog.MemberPickerFragment
import com.example.teambuilder.util.Utils.resetTeam
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TeamBuildFragment : BaseFragment<FragmentTeamBuildBinding>(R.layout.fragment_team_build) {
    private val viewModel: TeamBuildViewModel by viewModels()
    private var isSelectingMethodVisible = false
    private var isMemberCountVisible = false
    private var isBuildButtonVisible = false
    private var isConfirmButtonAvailable = false
    private var isBuiltTeamsExist = false

    private val fadeIn1: Animation by lazy { loadAnimation(requireContext(), R.anim.fade_in_1) }
    private val fadeIn2: Animation by lazy { loadAnimation(requireContext(), R.anim.fade_in_2) }
    private val fadeIn3: Animation by lazy { loadAnimation(requireContext(), R.anim.fade_in_3) }
    private val fadeIn4: Animation by lazy { loadAnimation(requireContext(), R.anim.fade_in_4) }
    private val fadeIn5: Animation by lazy { loadAnimation(requireContext(), R.anim.fade_in_5) }

    override fun proceed() {
        binding.fragment = this@TeamBuildFragment
        startAnim()
    }

    private fun startAnim() {
        binding.textView3.startAnimation(fadeIn1)
        binding.btnTeamALeader.startAnimation(fadeIn1)
        binding.btnTeamBLeader.startAnimation(fadeIn1)

        binding.tvSelectingMethod.startAnimation(fadeIn2)
        binding.rgMemberSelectingMethod.startAnimation(fadeIn2)
        binding.tvMemberCount.startAnimation(fadeIn2)
        binding.rgNVsN.startAnimation(fadeIn2)
        binding.btnBuildTeam.startAnimation(fadeIn2)
    }

    fun onClickALeader(view: View) {
        viewModel.players.value?.let {
            ChoiceDialog(it, "A팀 리더 선택") {
                binding.tvALeader.apply {
                    text = it.name
                    setTextColor(getColor(R.color.point_color))
                    viewModel.setALeader(it)
                }
            }.show(childFragmentManager, "set_a_leader")
        }
    }

    fun onClickBLeader(view: View) {
        viewModel.players.value?.let {
            ChoiceDialog(it, "B팀 리더 선택") {
                binding.tvBLeader.apply {
                    text = it.name
                    setTextColor(getColor(R.color.point_color))
                    viewModel.setBLeader(it)
                }
            }.show(childFragmentManager, "set_b_leader")
        }
    }

    fun onClickRandom(view: View) {
        if (!isBuiltTeamsExist) {
            if (isSelectingMethodVisible) {
                viewModel.setRandom()
            }
        } else {

        }
    }

    fun onClickPicking(view: View) {
        if (!isBuiltTeamsExist) {
            if (isSelectingMethodVisible) {
                viewModel.setPicking()
            }
        } else {

        }
    }

    fun onClickSix(view: View) {
        if (!isBuiltTeamsExist) {
            if (isMemberCountVisible) {
                viewModel.setSix()
            }
        } else {

        }
    }

    fun onClickSeven(view: View) {
        if (!isBuiltTeamsExist) {
            if (isMemberCountVisible) {
                viewModel.setSeven()
            }
        } else {

        }
    }

    fun onClickSetMembers(view: View) {
        if (isBuildButtonVisible) {
            when {
                viewModel.isRandom.value == true -> {

                }
                viewModel.isPicking.value == true -> {
                    MemberPickerFragment(
                        memberCount = if (viewModel.isSix.value == true) {
                            6
                        } else {
                            7
                        },
                        viewModel.aLeader.value!!,
                        viewModel.bLeader.value!!,
                        viewModel.players.value!!,
                        onResult = {
                            playColorAnimation(
                                binding.btnConfirmTeam,
                                getColor(R.color.gray),
                                getColor(R.color.point_color)
                            )
                            isBuiltTeamsExist = true
                        }
                    ).show(childFragmentManager, "member_picker")
                }
            }
        }
    }

    fun onClickConfirmTeams(view: View) {

    }

    override fun setObserver() {
        viewModel.aLeader.onChanged {
            if (viewModel.bLeader.value != null) {
                if (!isSelectingMethodVisible) {
                    isSelectingMethodVisible = true
                    binding.tvSelectingMethod.startAnimation(fadeIn3)
                    binding.rgMemberSelectingMethod.startAnimation(fadeIn3)
                }
            }
        }
        viewModel.bLeader.onChanged {
            if (viewModel.aLeader.value != null) {
                if (!isSelectingMethodVisible) {
                    isSelectingMethodVisible = true
                    binding.tvSelectingMethod.startAnimation(fadeIn3)
                    binding.rgMemberSelectingMethod.startAnimation(fadeIn3)
                }
            }
        }

        viewModel.isRandom.onChanged {
            setColorBySelection(binding.btnRandom, it)
            if (!isMemberCountVisible) {
                isMemberCountVisible = true
                binding.tvMemberCount.startAnimation(fadeIn4)
                binding.rgNVsN.startAnimation(fadeIn4)
            }
        }

        viewModel.isPicking.onChanged {
            setColorBySelection(binding.btnPicking, it)
            if (!isMemberCountVisible) {
                isMemberCountVisible = true
                binding.tvMemberCount.startAnimation(fadeIn4)
                binding.rgNVsN.startAnimation(fadeIn4)
            }
        }

        viewModel.isSix.onChanged {
            setColorBySelection(binding.btn6Vs6, it)
            if (!isBuildButtonVisible) {
                isBuildButtonVisible = true
                binding.btnBuildTeam.startAnimation(fadeIn5)
            }
        }

        viewModel.isSeven.onChanged {
            setColorBySelection(binding.btn7Vs7, it)
            if (!isBuildButtonVisible) {
                isBuildButtonVisible = true
                binding.btnBuildTeam.startAnimation(fadeIn5)
            }
        }
    }

    private fun setColorBySelection(view: View, boolean: Boolean) {
        if (boolean) {
            playColorAnimation(view, getColor(R.color.white), getColor(R.color.point_color))
            (view as TextView).setTextColor(getColor(R.color.white))
        } else {
            playColorAnimation(view, getColor(R.color.point_color), getColor(R.color.white))
            (view as TextView).setTextColor(getColor(R.color.gray))
        }
    }
}


enum class Sequence {
    WAY,
    MEMBER_COUNT
}