package com.example.teambuilder.ui.component.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.teambuilder.R
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.databinding.FragmentMemberPickerBinding
import com.example.teambuilder.ui.component.adapter.MemberPickerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class MemberPickerFragment(
    private val teamMemberLimit: Int,
    private val entry: Queue<Player>,
    private val teamALeader: Player,
    private val teamBLeader: Player,
    private val teamA: MutableList<Player>?,
    private val teamB: MutableList<Player>?,
    private inline val onClickCancel: () -> Unit,
    private inline val onResult: (Pair<List<Player>, List<Player>>) -> Unit
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMemberPickerBinding
    private val adapterA = MemberPickerAdapter(onClickRemove = {
        removePlayer(it, true)
    }, teamALeader)
    private val adapterB = MemberPickerAdapter(onClickRemove = {
        removePlayer(it, false)
    }, teamBLeader)

    private var currentPlayer = Player(0, "", "", false)
    private val teamAList = teamA ?: mutableListOf(teamALeader)
    private val teamBList = teamB ?: mutableListOf(teamBLeader)
    private var isTeamAFull = false
    private var isTeamBFull = false

    // todo 필요없는 변수 있는지 확인 후 리팩토링

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_member_picker, container, false)
        isCancelable = false
        binding.fragment = this@MemberPickerFragment
        binding.adapterA = adapterA
        binding.adapterB = adapterB
        adapterA.submitList(teamA ?: listOf(teamALeader))
        adapterB.submitList(teamB ?: listOf(teamBLeader))

        initEntry()
        initView()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            isDraggable = false
        }
        return dialog
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialogTheme
    }

    private fun initEntry() {
        entry.remove(teamALeader)
        entry.remove(teamBLeader)
    }

    private fun initView() {
        binding.tvTeamAMemberCnt.text = "$teamMemberLimit / ${teamA?.size ?: 1}"
        binding.tvTeamBMemberCnt.text = "$teamMemberLimit / ${teamB?.size ?: 1}"

        if (teamA?.isNotEmpty() == true) {
            isTeamAFull = true
            isTeamBFull = true
        }

        pollAndSetView()
        binding.btnTeamA.setOnClickListener {
            if (!isTeamAFull) {
                teamAList.add(currentPlayer)
                adapterA.submitList(teamAList.toMutableList())
                binding.tvTeamAMemberCnt.text = "$teamMemberLimit / ${teamAList.size}"
                binding.rvTeamA.smoothScrollToPosition(teamAList.size - 1)
                currentPlayer.team = 1
                pollAndSetView()

                if (teamMemberLimit == teamAList.size) {
                    isTeamAFull = true
                }
                checkBothTeamsAreFull()
            }
        }

        binding.btnTeamB.setOnClickListener {
            if (!isTeamBFull) {
                teamBList.add(currentPlayer)
                adapterB.submitList(teamBList.toMutableList())
                binding.tvTeamBMemberCnt.text = "$teamMemberLimit / ${teamBList.size}"
                binding.rvTeamB.smoothScrollToPosition(teamBList.size - 1)
                currentPlayer.team = 2
                pollAndSetView()

                if (teamMemberLimit == teamBList.size) {
                    isTeamBFull = true
                }
                checkBothTeamsAreFull()
            }
        }

        binding.btnExcept.setOnClickListener {
            pollAndSetView()
            checkEntryIsEmpty()
        }

        binding.btnCancel.setOnClickListener {
            onClickCancel()
            dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            checkBothTeamsAreFull()
        }
    }

    private fun pollAndSetView() {
        val polled = entry.poll()!!
        binding.tvName.text = polled.name
        binding.tvAffiliation.text = polled.affiliation
        currentPlayer = polled
    }

    private fun removePlayer(player: Player, isTeamA: Boolean) {
        if (isTeamA) {
            teamAList.remove(player)
            adapterA.submitList(teamAList.toMutableList())
            binding.tvTeamAMemberCnt.text = "$teamMemberLimit / ${teamAList.size}"
            isTeamAFull = false
        } else {
            teamBList.remove(player)
            adapterB.submitList(teamBList.toMutableList())
            binding.tvTeamBMemberCnt.text = "$teamMemberLimit / ${teamBList.size}"
            isTeamBFull = false
        }
        entry.offer(player)
    }

    private fun checkEntryIsEmpty() {
        // todo 엔트리가 비었는지
    }

    @Suppress("UNCHECKED_CAST")
    private fun checkBothTeamsAreFull() {
        if (isTeamAFull && isTeamBFull) {
            DefaultDialog("팀 설정 완료", "팀이 모두 구성됐습니다.", "재확인", "완료",
                Pair(teamAList, teamBList),
                onClickCancel = {

                },
                onClickConfirm = {
                    onResult(it as Pair<List<Player>, List<Player>>)
                    dismiss()
                }).show(childFragmentManager, "confirm")
        }
    }
}