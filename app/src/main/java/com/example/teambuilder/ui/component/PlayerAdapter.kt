package com.example.teambuilder.ui.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
                    tvSuperPlayer.visibility = View.VISIBLE
                }
            }
        }
    }
}