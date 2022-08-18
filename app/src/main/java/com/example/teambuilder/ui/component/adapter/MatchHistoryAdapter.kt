package com.example.teambuilder.ui.component.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teambuilder.data.model.Match
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.databinding.ItemMatchBinding
import com.example.teambuilder.util.GenericDiffUtil
import com.example.teambuilder.util.Utils.typePlayerList
import com.google.gson.Gson

class MatchHistoryAdapter(
) : ListAdapter<Match, RecyclerView.ViewHolder>(GenericDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemMatchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(match: Match) {
            with(binding) {
                if (match.endDate == "") { // 경기 진행 중
                    tvState.text = "진행중인 경기입니다."
                    tvState.visibility = View.VISIBLE
                    layMatch.alpha = 0.3f
                } else if (match.winnerScore == match.loserScore) {
                    tvState.visibility = View.VISIBLE
                    tvState.text = "무승부입니다."
                    layMatch.alpha = 0.3f
                } else {
                    tvState.visibility = View.GONE
                    layMatch.alpha = 1f
                }

                tvScore.text = "${match.winnerScore} : ${match.loserScore}"

                val stringBuilderTeamA = StringBuilder()
                val stringBuilderTeamB = StringBuilder()
                val gson = Gson()
                val teamA: List<Player> = gson.fromJson(match.teamAPlayers, typePlayerList)
                val teamB: List<Player> = gson.fromJson(match.teamBPlayers, typePlayerList)

                teamA.forEach {
                    stringBuilderTeamA.append("${it.name} ")
                }
                teamB.forEach {
                    stringBuilderTeamB.append("${it.name} ")
                }

                when (match.winner) {
                    "A" -> {
                        tvTeamWin.text = "${teamA.first().name} 팀"
                        tvTeamLose.text = "${teamB.first().name} 팀"
                        tvPlayersWin.text = stringBuilderTeamA.toString()
                        tvPlayersLose.text = stringBuilderTeamB.toString()
                    }
                    "B" -> {
                        tvTeamWin.text = "${teamB.first().name} 팀"
                        tvTeamLose.text = "${teamA.first().name} 팀"
                        tvPlayersWin.text = stringBuilderTeamB.toString()
                        tvPlayersLose.text = stringBuilderTeamA.toString()
                    }
                    else -> {
                        tvTeamWin.text = "${teamA.first().name} 팀"
                        tvTeamLose.text = "${teamB.first().name} 팀"
                        tvPlayersWin.text = ""
                        tvPlayersLose.text = ""
                    }
                }

                tvEndDate.text = match.endDate
            }
        }
    }
}