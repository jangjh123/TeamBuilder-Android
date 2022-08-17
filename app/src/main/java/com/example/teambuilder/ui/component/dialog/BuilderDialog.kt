package com.example.teambuilder.ui.component.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.teambuilder.R
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.data.model.Team
import com.example.teambuilder.databinding.DialogBuilderBinding
import com.example.teambuilder.ui.component.adapter.TeamBuilderAdapter
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

class BuilderDialog(
    private val isRandom: Boolean,
    private val memberCount: Int,
    private val teamALeader: Player,
    private val teamBLeader: Player,
    private val players: List<Player>,
    private inline val onTeamBuilt: () -> Unit
) : BottomSheetDialogFragment() {
    private lateinit var binding: DialogBuilderBinding

    private val adapterA = TeamBuilderAdapter(onClickRemove = {
        removePlayer(it, true)
    }, teamALeader)

    private val adapterB = TeamBuilderAdapter(onClickRemove = {
        removePlayer(it, false)
    }, teamBLeader)

    private var currentPlayer = Player(0, "", "", false, 0)

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
            DataBindingUtil.inflate(inflater, R.layout.dialog_builder, container, false)
        isCancelable = false
        binding.dialog = this@BuilderDialog
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
        teamA = ArrayList<Player>()
        teamB = ArrayList<Player>()

        if (isRandom) {
            val tempPlayers = ArrayList<Player>()
            players.forEach {
                if (it.team == Team.RANDOM) {
                    tempPlayers.add(it)
                }
            }
            val set = mutableSetOf<Int>()
            while (set.size < (memberCount - 1) * 2) {
                set.add((0 until tempPlayers.size).random())
            }

            var cnt = 0

            set.forEach {
                cnt++
                if (cnt < memberCount) {
                    tempPlayers[it].team = Team.TEAM_A
                } else {
                    tempPlayers[it].team = Team.TEAM_B
                }
            }
        }

        players.forEach {
            if (it.team == Team.TEAM_A) {
                teamA.add(it)
            } else if (it.team == Team.TEAM_B) {
                teamB.add(it)
            }
        }

        adapterA.setList(teamA)
        adapterB.setList(teamB)

        if (teamA.size == memberCount && teamB.size == memberCount) {
            isTeamAFull = true
            isTeamBFull = true
        }

        checkBothTeamsAreFull()
    }

    private fun initEntry() {
        if (isRandom) {
            entry = LinkedList<Player>().apply {
                players.forEach {
                    if (it.team == Team.RANDOM) {
                        offer(it)
                    }
                }
            }
        } else {
            entry = LinkedList<Player>().apply {
                players.forEach {
                    if (it.team == Team.NONE) {
                        offer(it)
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.tvTeamAMemberCnt.text = "$memberCount / ${teamA?.size}"
        binding.tvTeamBMemberCnt.text = "$memberCount / ${teamB?.size}"

        pollAndSetView()
    }

    fun onClickTeamA(view: View) {
        if (teamA.size != memberCount) {
            adapterA.addPlayer(currentPlayer)
            binding.tvTeamAMemberCnt.text = "$memberCount / ${teamA.size}"
            binding.rvTeamA.smoothScrollToPosition(teamA.size - 1)
            currentPlayer.team = Team.TEAM_B
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
            currentPlayer.team = Team.TEAM_A
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
            binding.ivAffiliation.setImageResource(
                when (polled.affiliation) {
                    "경북지역 인자위" -> {
                        R.drawable.logo_0
                    }
                    "경북산학융합원" -> {
                        R.drawable.logo_1
                    }
                    "새마을세계화재단" -> {
                        R.drawable.logo_2
                    }
                    "한국여성경제인협회" -> {
                        R.drawable.logo_3
                    }
                    else -> {
                        R.drawable.logo_free_agent
                    }
                }
            )
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

                    for (i in teamA.size - 1 downTo 1) {
                        teamA[i].team = Team.NONE
                        adapterA.removePlayer(teamA[i])
                    }
                    for (i in teamB.size - 1 downTo 1) {
                        teamB[i].team = Team.NONE
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
            player.team = Team.NONE
            entry.offer(player)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun checkBothTeamsAreFull() {
        if (isTeamAFull && isTeamBFull) {
            CoroutineScope(Dispatchers.Default).launch {
                delay(200L)
                DefaultDialog("팀 설정 완료", "팀이 모두 구성됐습니다.", "재확인", "완료",
                    Pair(teamA, teamB),
                    onClickCancel = {

                    },
                    onClickConfirm = {
                        onTeamBuilt()
                        dismiss()
                    }).show(childFragmentManager, "confirm")
            }
        }
    }

    private fun showSnackBar(text: String) {
        Snackbar.make(dialog!!.window!!.decorView, text, Snackbar.LENGTH_SHORT).show()
    }
}