package com.example.teambuilder.ui.component.dialog

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
import com.example.teambuilder.util.Utils.resetTeam
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MemberPickerFragment(
    private val memberCount: Int,
    private val teamALeader: Player,
    private val teamBLeader: Player,
    private val players: List<Player>,
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

    private lateinit var teamA: ArrayList<Player>
    private lateinit var teamB: ArrayList<Player>
    private lateinit var entry: Queue<Player>

    private var isTeamAFull = false
    private var isTeamBFull = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            isDraggable = false
        }
        return dialog
    }

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

        initTeams()
        initEntry()
        initView()
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialogTheme
    }

    private fun initTeams() {
        teamA = ArrayList<Player>().apply {
            players.forEach {
                if (it.team == 1) {
                    add(it)
                }
            }
        }
        adapterA.setList(teamA)
        teamB = ArrayList<Player>().apply {
            players.forEach {
                if (it.team == 2) {
                    add(it)
                }
            }
        }
        adapterB.setList(teamB)

        if (teamA.size == memberCount && teamB.size == memberCount) {
            isTeamAFull = true
            isTeamBFull = true
        }
    }

    private fun initEntry() {
        entry = LinkedList<Player>().apply {
            players.forEach {
                if (it.team == 0) {
                    offer(it)
                }
            }
        }
    }

    private fun initView() {
        binding.tvTeamAMemberCnt.text = "$memberCount / ${teamA?.size ?: 1}"
        binding.tvTeamBMemberCnt.text = "$memberCount / ${teamB?.size ?: 1}"

        pollAndSetView()
    }

    fun onClickTeamA(view: View) {
        if (teamA.size != memberCount) {
            adapterA.addPlayer(currentPlayer)
            binding.tvTeamAMemberCnt.text = "$memberCount / ${teamA.size}"
            binding.rvTeamA.smoothScrollToPosition(teamA.size - 1)
            currentPlayer.team = 2
            pollAndSetView()

            if (memberCount == teamA.size) {
                isTeamAFull = true
            }
            checkBothTeamsAreFull()
        } else {
            showSnackBar("정원을 초과할 수 없습니다.")
        }
    }

    fun onClickTeamB(view: View) {
        if (teamB.size != memberCount) {
            adapterB.addPlayer(currentPlayer)
            binding.tvTeamBMemberCnt.text = "$memberCount / ${teamB.size}"
            binding.rvTeamB.smoothScrollToPosition(teamB.size - 1)
            currentPlayer.team = 1
            pollAndSetView()

            if (memberCount == teamB.size) {
                isTeamBFull = true
            }
            checkBothTeamsAreFull()
        } else {
            showSnackBar("정원을 초과할 수 없습니다.")
        }
    }

    fun onClickExcept(view: View) {
        pollAndSetView()
    }

    fun onClickCancel(view: View) {
        DefaultDialog(
            "팀 구성 취소",
            "현재 구성 중인 팀이 삭제됩니다.",
            "취소",
            "확인",
            null,
            null,
            onClickConfirm = {
                resetTeam(players, teamALeader, teamBLeader)
                dismiss()
            },
        ).show(childFragmentManager, "cancel_team_building")
    }

    fun onClickConfirm(view: View) {
        checkBothTeamsAreFull()
    }

    private fun pollAndSetView() {
        if (entry.size != 0) {
            val polled = entry.poll()!!
            binding.tvName.text = polled.name
            binding.tvAffiliation.text = polled.affiliation
            currentPlayer = polled
        } else {
            binding.tvName.text = ""
            binding.tvAffiliation.text = ""
            DefaultDialog(
                "팀 재구성",
                "엔트리가 비었습니다.\n팀을 재구성합니다.",
                "취소",
                "확인",
                null,
                null,
                onClickConfirm = {
                    resetTeam(players, teamALeader, teamBLeader)
                    players.forEach {
                        if (it != teamALeader && it != teamBLeader) {
                            entry.offer(it)
                        }
                    }

                    for (i in memberCount - 1 downTo 1) {
                        teamA[i].team = 0
                        teamB[i].team = 0
                        adapterA.removePlayer(teamA[i])
                        adapterB.removePlayer(teamB[i])
                    }
                    isTeamAFull = false
                    isTeamBFull = false
                    initView()
                }
            ).show(childFragmentManager, "entry_is_empty")
        }
    }

    private fun removePlayer(player: Player, isTeamA: Boolean) {
        if (player == teamALeader || player == teamBLeader) {
            showSnackBar("리더는 삭제할 수 없습니다.")
        } else {
            if (isTeamA) {
                adapterA.removePlayer(player)
                binding.tvTeamAMemberCnt.text = "$memberCount / ${teamA.size}"
                isTeamAFull = false
            } else {
                adapterB.removePlayer(player)
                binding.tvTeamBMemberCnt.text = "$memberCount / ${teamB.size}"
                isTeamBFull = false
            }
            player.team = 0
            entry.offer(player)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun checkBothTeamsAreFull() {
        // todo 리팩토링 예정
        if (isTeamAFull && isTeamBFull) {
            CoroutineScope(Dispatchers.Default).launch {
                delay(200L)
                DefaultDialog("팀 설정 완료", "팀이 모두 구성됐습니다.", "재확인", "완료",
                    Pair(teamA, teamB),
                    onClickCancel = {

                    },
                    onClickConfirm = {
                        onResult(it as Pair<List<Player>, List<Player>>)
                        dismiss()
                    }).show(childFragmentManager, "confirm")
            }
        }
    }

    private fun showSnackBar(text: String) {
        Snackbar.make(dialog!!.window!!.decorView, text, Snackbar.LENGTH_SHORT).show()
    }
}