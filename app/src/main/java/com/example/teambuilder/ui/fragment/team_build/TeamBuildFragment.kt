package com.example.teambuilder.ui.fragment.team_build

import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.teambuilder.R
import com.example.teambuilder.databinding.FragmentTeamBuildBinding
import com.example.teambuilder.ui.BaseFragment
import com.example.teambuilder.ui.component.dialog.BuilderDialog
import com.example.teambuilder.ui.component.dialog.ChoiceDialog
import com.example.teambuilder.ui.component.dialog.DefaultDialog
import com.example.teambuilder.ui.component.dialog.SelectionDialog
import com.example.teambuilder.util.Utils.resetTeam
import com.example.teambuilder.util.isNotEmpty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TeamBuildFragment : BaseFragment<FragmentTeamBuildBinding>(R.layout.fragment_team_build) {
    private val viewModel: TeamBuildViewModel by viewModels()
    private var isBuiltTeamsExist = false

    private val fadeIn1: Animation by lazy { loadAnimation(requireContext(), R.anim.fade_in_1) }
    private val fadeIn2: Animation by lazy { loadAnimation(requireContext(), R.anim.fade_in_2) }
    private val fadeIn3: Animation by lazy { loadAnimation(requireContext(), R.anim.fade_in_3) }
    private val fadeIn4: Animation by lazy { loadAnimation(requireContext(), R.anim.fade_in_4) }
    private val fadeIn5: Animation by lazy { loadAnimation(requireContext(), R.anim.fade_in_5) }
    private val fadeOut1: Animation by lazy { loadAnimation(requireContext(), R.anim.fade_out_1) }

    override fun proceed() {
        binding.fragment = this@TeamBuildFragment
        startAnim()
        initTouchable()
    }

    private fun startAnim() {
        binding.tvSelectLeader.startAnimation(fadeIn1)
        binding.btnTeamALeader.startAnimation(fadeIn1)
        binding.btnTeamBLeader.startAnimation(fadeIn1)

        binding.tvSelectingMethod.startAnimation(fadeIn2)
        binding.rgMemberSelectingMethod.startAnimation(fadeIn2)
        binding.tvMemberCount.startAnimation(fadeIn2)
        binding.rgNVsN.startAnimation(fadeIn2)
        binding.btnBuildTeam.startAnimation(fadeIn2)
    }

    private fun initTouchable() {
        setTouchable(binding.btnRandom, false)
        setTouchable(binding.btnPicking, false)
        setTouchable(binding.btnRandom, false)
        setTouchable(binding.btnPicking, false)
        setTouchable(binding.btn6Vs6, false)
        setTouchable(binding.btn7Vs7, false)
        setTouchable(binding.btnBuildTeam, false)
        setTouchable(binding.btnConfirmTeam, false)
    }

    fun onClickALeader(view: View) {
        viewModel.players.isNotEmpty {
            ChoiceDialog(viewModel.players, "A팀 리더 선택") {
                binding.tvALeader.apply {
                    text = it.name
                    setTextColor(getColor(R.color.point_color))
                    viewModel.setTeamALeader(it)
                }
            }.show(childFragmentManager, "set_a_leader")
        }
    }

    fun onClickBLeader(view: View) {
        viewModel.players.isNotEmpty {
            ChoiceDialog(viewModel.players, "B팀 리더 선택") {
                binding.tvBLeader.apply {
                    text = it.name
                    setTextColor(getColor(R.color.point_color))
                    viewModel.setTeamBLeader(it)
                }
            }.show(childFragmentManager, "set_b_leader")
        }
    }

    fun onClickRandom(view: View) {
        if (!isBuiltTeamsExist) {
            viewModel.setRandom(true)
        } else {
            viewModel.isPicking.isTrue {
                showRebuildDialog(Sequence.SELECTING_METHOD) {
                    viewModel.setRandom(true)
                }
            }
        }
    }

    fun onClickPicking(view: View) {
        if (!isBuiltTeamsExist) {
            viewModel.setPicking(true)
        } else {
            viewModel.isRandom.isTrue {
                showRebuildDialog(Sequence.SELECTING_METHOD) {
                    viewModel.setPicking(true)
                }
            }
        }
    }

    fun onClickSix(view: View) {
        if (!isBuiltTeamsExist) {
            viewModel.setSix(true)
        } else {
            viewModel.isSeven.isTrue {
                showRebuildDialog(Sequence.MEMBER_COUNT) {
                    viewModel.setSix(true)
                }
            }
        }
    }

    fun onClickSeven(view: View) {
        if (!isBuiltTeamsExist) {
            viewModel.setSeven(true)
        } else {
            viewModel.isSix.isTrue {
                showRebuildDialog(Sequence.MEMBER_COUNT) {
                    viewModel.setSeven(true)
                }
            }
        }
    }

    fun onClickSetMembers(view: View) {
        var memberCount = 0
        viewModel.isSix.isTrue {
            memberCount = 6
        }
        viewModel.isSeven.isTrue {
            memberCount = 7
        }

        viewModel.isRandom.isTrue {
            SelectionDialog(
                viewModel.players,
                onClickCancel = {
                    resetTeam(
                        viewModel.players,
                        viewModel.teamALeader.value!!,
                        viewModel.teamBLeader.value!!
                    )
                },
                onClickConfirm = {
                    BuilderDialog(
                        true,
                        memberCount,
                        viewModel.teamALeader.value!!,
                        viewModel.teamBLeader.value!!,
                        viewModel.players,
                        onTeamBuilt = {
                            isBuiltTeamsExist = true
                            viewModel.setConfirmButtonAvailable(true)
                        }
                    ).show(childFragmentManager, "random_builder")
                }
            ).show(childFragmentManager, "selection")
        }

        viewModel.isPicking.isTrue {
            BuilderDialog(
                false,
                memberCount,
                viewModel.teamALeader.value!!,
                viewModel.teamBLeader.value!!,
                viewModel.players,
                onTeamBuilt = {
                    isBuiltTeamsExist = true
                    viewModel.setConfirmButtonAvailable(true)
                }
            ).show(childFragmentManager, "member_picker")
        }
    }

    fun onClickConfirmTeams(view: View) {
        viewModel.setTeamExist()
        viewModel.setMatch()
        findNavController().navigate(TeamBuildFragmentDirections.actionFragTeamBuildToFragMatch())
    }

    override fun setObserver() {
        viewModel.teamALeader.onChanged {
            if (viewModel.teamBLeader.value != null) {
                if (viewModel.isSelectingMethodVisible.value == null) {
                    viewModel.setSelectingMethodVisible(true)
                }
            }
        }
        viewModel.teamBLeader.onChanged {
            if (viewModel.teamALeader.value != null) {
                viewModel.isSelectingMethodVisible.isNullOrFalse {
                    viewModel.setSelectingMethodVisible(true)
                }
            }
        }

        viewModel.isRandom.onChanged {
            setColorBySelection(binding.btnRandom, it)
            viewModel.isMemberCountVisible.isNullOrFalse {
                viewModel.setMemberCountVisible(true)
            }
        }

        viewModel.isPicking.onChanged {
            setColorBySelection(binding.btnPicking, it)
            viewModel.isMemberCountVisible.isNullOrFalse {
                viewModel.setMemberCountVisible(true)
            }
        }

        viewModel.isSelectingMethodVisible.onChanged {
            if (it) {
                binding.tvSelectingMethod.startAnimation(fadeIn3)
                binding.rgMemberSelectingMethod.startAnimation(fadeIn3)
                setTouchable(binding.btnRandom, true)
                setTouchable(binding.btnPicking, true)
            } else {
                viewModel.isRandom.isTrue {
                    setColorBySelection(binding.btnRandom, false)
                }
                viewModel.isRandom.isNullOrFalse {
                    setColorBySelection(binding.btnPicking, false)
                }

                binding.tvSelectingMethod.startAnimation(fadeOut1)
                binding.rgMemberSelectingMethod.run {
                    startAnimation(fadeOut1)
                    setTouchable(this, false)
                }
            }
        }

        viewModel.isSix.onChanged {
            setColorBySelection(binding.btn6Vs6, it)
            viewModel.isBuildButtonVisible.isNullOrFalse {
                viewModel.setBuildButtonVisible(true)
            }
        }

        viewModel.isSeven.onChanged {
            setColorBySelection(binding.btn7Vs7, it)
            viewModel.isBuildButtonVisible.isNullOrFalse {
                viewModel.setBuildButtonVisible(true)
            }
        }

        viewModel.isMemberCountVisible.onChanged {
            if (it) {
                binding.tvMemberCount.startAnimation(fadeIn4)
                binding.rgNVsN.startAnimation(fadeIn4)
                setTouchable(binding.btn6Vs6, true)
                setTouchable(binding.btn7Vs7, true)
            } else {
                viewModel.isSix.isTrue {
                    setColorBySelection(binding.btn6Vs6, false)
                }
                viewModel.isSix.isNullOrFalse {
                    setColorBySelection(binding.btn7Vs7, false)
                }

                binding.tvMemberCount.startAnimation(fadeOut1)
                binding.rgNVsN.startAnimation(fadeOut1)
                setTouchable(binding.btn6Vs6, false)
                setTouchable(binding.btn7Vs7, false)
            }
        }

        viewModel.isBuildButtonVisible.onChanged {
            if (it) {
                binding.btnBuildTeam.run {
                    startAnimation(fadeIn5)
                    setTouchable(this, true)
                }
            } else {
                binding.btnBuildTeam.run {
                    startAnimation(fadeOut1)
                    setTouchable(this, false)
                }
            }
        }

        viewModel.isConfirmButtonAvailable.onChanged {
            if (it) {
                playColorAnimation(
                    binding.btnConfirmTeam,
                    getColor(R.color.gray),
                    getColor(R.color.point_color)
                )
                setTouchable(binding.btnConfirmTeam, true)
            } else {
                playColorAnimation(
                    binding.btnConfirmTeam,
                    getColor(R.color.point_color),
                    getColor(R.color.gray)
                )
                setTouchable(binding.btnConfirmTeam, false)
            }
        }

        viewModel.errorInfo.observe(viewLifecycleOwner) {
            DefaultDialog(
                "네트워크 오류",
                "인터넷 연결 상태를 확인 후\n다시 시도해주세요.",
                "종료",
                "재시도",
                null,
                onClickCancel = {
                    requireActivity().finish()
                },
                onClickConfirm = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.getPlayers()
                    }
                }
            ).show(childFragmentManager, "error_occurred")
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

    private inline fun showRebuildDialog(sequence: Sequence, crossinline onReset: () -> Unit) {
        DefaultDialog(
            "팀 재구성",
            "설정이 변경되었습니다.\n팀을 재구성합니다.",
            "취소",
            "확인",
            null,
            null,
            onClickConfirm = {
                resetTeam(
                    viewModel.players,
                    viewModel.teamALeader.value!!,
                    viewModel.teamBLeader.value!!
                )
                isBuiltTeamsExist = false
                when (sequence) {
                    Sequence.SELECTING_METHOD -> {
                        viewModel.setRandom(false)
                        viewModel.setPicking(false)
                        viewModel.setSix(false)
                        viewModel.setSeven(false)
                        viewModel.setBuildButtonVisible(false)
                        viewModel.setConfirmButtonAvailable(false)
                    }
                    Sequence.MEMBER_COUNT -> {
                        viewModel.setSix(false)
                        viewModel.setSeven(false)
                        viewModel.setConfirmButtonAvailable(false)
                    }
                }

                onReset()
            }
        ).show(childFragmentManager, "rebuild_team")
    }
}

enum class Sequence {
    SELECTING_METHOD,
    MEMBER_COUNT
}