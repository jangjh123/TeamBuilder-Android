package com.example.teambuilder.ui.fragment.team_build

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.example.teambuilder.R
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.databinding.FragmentTeamBuildBinding
import com.example.teambuilder.ui.BaseFragment
import com.example.teambuilder.ui.component.dialog.ChoiceDialog
import com.example.teambuilder.ui.component.dialog.DefaultDialog
import com.example.teambuilder.ui.component.dialog.MemberPickerFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class TeamBuildFragment : BaseFragment<FragmentTeamBuildBinding>(R.layout.fragment_team_build) {
    private val viewModel: TeamBuildViewModel by viewModels()

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

    override fun proceed() {
        binding.fragment = this@TeamBuildFragment
        startAnim()
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

    fun onClickALeader(view: View) {
        ChoiceDialog(viewModel.players.value!!, "A팀 리더 선택") {
            binding.tvALeader.apply {
                text = it.name
                setTextColor(getColor(R.color.point_color))
                viewModel.setALeader(it)
            }
        }.show(childFragmentManager, "set_a_leader")
    }

    fun onClickBLeader(view: View) {
        ChoiceDialog(viewModel.players.value!!, "B팀 리더 선택") {
            binding.tvBLeader.apply {
                text = it.name
                setTextColor(getColor(R.color.point_color))
                viewModel.setBLeader(it)
            }
        }.show(childFragmentManager, "set_b_leader")
    }

    fun onClickRandom(view: View) = viewModel.setRandom()
    fun onClickPicking(view: View) = viewModel.setPicking()
    fun onClickSix(view: View) = viewModel.setSix()
    fun onClickSeven(view: View) = viewModel.setSeven()

    fun onClickSetMembers(view: View) {
//        if (isSetTeamMembersVisible) {
//            when {
//                isRandom -> {
//
//                }
//                isDirect -> {
//                    MemberPickerFragment(
//                        teamMemberLimit =
//                        if (isSix) {
//                            6
//                        } else {
//                            7
//                        },
//                        entry = LinkedList<Player>().apply {
//                            viewModel.players.value?.let { players ->
//                                players.forEach { player ->
//                                    if (player.team == 0) {
//                                        add(player)
//                                    }
//                                }
//                            }
//                        },
//                        teamALeader = viewModel.aLeader.value!!,
//                        teamBLeader = viewModel.bLeader.value!!,
//                        viewModel.teamA.ifEmpty {
//                            null
//                        } as MutableList<Player>?,
//                        viewModel.teamB.ifEmpty {
//                            null
//                        } as MutableList<Player>?,
//                        onClickCancel = {
//                            viewModel.resetTeam()
//                        },
//                        onResult = {
//                            viewModel.setATeam(it.first)
//                            viewModel.setBTeam(it.second)
//
//                            playColorAnimation(
//                                binding.btnConfirmTeam,
//                                getColor(R.color.gray),
//                                getColor(R.color.point_color)
//                            )
//                        }).show(childFragmentManager, "member_picker")
//                }
//            }
//        }
    }

    fun onClickConfirmTeams(view: View) {

    }

    private fun isTeamsExist(): Boolean =
        viewModel.teamA.isNotEmpty() || viewModel.teamB.isNotEmpty()

    private fun rebuildTeam(sequence: Sequence) {
        DefaultDialog(
            "팀 재구성",
            "이미 구성된 팀이 있습니다.\n재구성을 진행합니다.",
            "취소",
            "확인",
            null,
            null,
            onClickConfirm = {
                viewModel.resetTeam()

//                when (sequence) {
//                    Sequence.WAY -> {
//                        if (isRandom) {
//                            isRandom = false
//                            resetButtonColor(binding.btnRandom)
//                        } else if (isDirect) {
//                            isDirect = false
//                            resetButtonColor(binding.btnPicking)
//                        }
//
//                        if (isSix) {
//                            isSix = false
//                            resetButtonColor(binding.btn6Vs6)
//                        } else if (isSeven) {
//                            isSeven = false
//                            resetButtonColor(binding.btn7Vs7)
//                        }
//                    }
//                    Sequence.MEMBER_COUNT -> {
//                        if (isSix) {
//                            isSix = false
//                            resetButtonColor(binding.btn6Vs6)
//                        } else if (isSeven) {
//                            isSeven = false
//                            resetButtonColor(binding.btn7Vs7)
//                        }
//                    }
//                }
            }
        ).show(childFragmentManager, "rebuild")
    }

    override fun setObserver() {
        viewModel.isRandom.onChanged { setColorBySelection(binding.btnRandom, it) }
        viewModel.isPicking.onChanged { setColorBySelection(binding.btnPicking, it) }
        viewModel.isSix.onChanged { setColorBySelection(binding.btn6Vs6, it) }
        viewModel.isSeven.onChanged { setColorBySelection(binding.btn7Vs7, it) }
    }

    private inline fun LiveData<Boolean>.onChanged(crossinline onChanged: (Boolean) -> Unit) {
        this.observe(viewLifecycleOwner) {
            onChanged(it)
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