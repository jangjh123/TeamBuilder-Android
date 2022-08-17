package com.example.teambuilder.ui.component.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.data.model.Team
import com.example.teambuilder.databinding.ItemPlayerBinding
import com.example.teambuilder.util.GenericDiffUtil

class PlayerAdapter(
    private val isSelection: Boolean,
    private inline val onClickPlayer: ((Player) -> Unit)?
) : ListAdapter<Player, RecyclerView.ViewHolder>(GenericDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemPlayerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val player = getItem(position)
            holder.bind(player)
        }
    }

    inner class ViewHolder(private val binding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Player) {
            with(binding) {
                tvName.text = player.name
                tvAffiliation.text = player.affiliation

                if (isSelection) { // 랜덤 팀원 선택시, 리더를 제외한 팀원의 team 값이 Team_RANDOM 으로 설정되어 들어옴.
                    tvSpecial.text = if (player.isLeader) {
                        "리더"
                    } else {
                        ""
                    }

                    if (player.team == Team.RANDOM) {
                        layPlayer.alpha = 1f
                        tvState.visibility = View.GONE
                    } else if (player.team == Team.NONE) {
                        layPlayer.alpha = 0.2f
                        tvState.visibility = View.VISIBLE
                        tvState.text = "제외됨"
                    }

                    cvPlayer.setOnClickListener {
                        if (player.team == Team.RANDOM) {
                            layPlayer.alpha = 0.2f
                            tvState.visibility = View.VISIBLE
                            tvState.text = "제외됨"
                            player.team = Team.NONE
                        } else if (player.team == Team.NONE) {
                            layPlayer.alpha = 1f
                            player.team = Team.RANDOM
                            tvState.visibility = View.GONE
                        }
                    }

                } else { // 리더 선택
                    tvSpecial.text = if (player.isSuperPlayer) {
                        "고수"
                    } else {
                        ""
                    }

                    if (player.team == Team.NONE) {
                        layPlayer.alpha = 1f
                        tvState.visibility = View.GONE
                    } else {
                        layPlayer.alpha = 0.2f
                        tvState.visibility = View.VISIBLE
                        tvState.text = "선택됨"
                    }

                    cvPlayer.setOnClickListener {
                        onClickPlayer?.invoke(player)
                    }
                }
            }
        }
    }
}