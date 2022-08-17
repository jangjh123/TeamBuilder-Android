package com.example.teambuilder.ui.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.data.model.Team
import com.example.teambuilder.databinding.ItemTeamABinding
import com.example.teambuilder.databinding.ItemTeamBBinding
import com.example.teambuilder.util.GenericDiffUtil

class TeamAdapter(
    private val team: Team
) : ListAdapter<Player, RecyclerView.ViewHolder>(GenericDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (team) {
            Team.TEAM_A -> {
                TeamAViewHolder(
                    ItemTeamABinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                TeamBViewHolder(
                    ItemTeamBBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    inner class TeamAViewHolder(private val binding: ItemTeamABinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Player) {
            with(binding) {
                tvName.text = player.name
                tvAffiliation.text = player.affiliation
            }
        }
    }

    inner class TeamBViewHolder(private val binding: ItemTeamBBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Player) {
            with(binding) {
                tvName.text = player.name
                tvAffiliation.text = player.affiliation
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (team) {
            Team.TEAM_A -> {
                (holder as TeamAViewHolder).bind(getItem(position))
            }
            Team.TEAM_B -> {
                (holder as TeamBViewHolder).bind(getItem(position))
            }
        }
    }
}