package com.example.teambuilder.ui.component.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teambuilder.R
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.databinding.ItemPlayerBinding
import com.example.teambuilder.util.GenericDiffUtil

class PlayerAdapter(private inline val onClickPlayer: (Player) -> Unit) :
    ListAdapter<Player, RecyclerView.ViewHolder>(GenericDiffUtil()) {

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
            val news = getItem(position)
            holder.bind(news)
        }
    }

    inner class ViewHolder(private val binding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Player) {
            with(binding) {
                tvName.text = player.name
                tvAffiliation.text = player.affiliation
                cvPlayer.setOnClickListener {
                    onClickPlayer(player)
                }

                if (player.isSuperPlayer) {
                    tvSuperPlayer.text = binding.root.context.getString(R.string.super_player)
                } else {
                    tvSuperPlayer.text = ""
                }

                if (player.team != 0) {
                    layPlayer.alpha = 0.2f
                    tvChosen.visibility = View.VISIBLE
                } else {
                    layPlayer.alpha = 1f
                    tvChosen.visibility = View.GONE
                }
            }
        }
    }
}