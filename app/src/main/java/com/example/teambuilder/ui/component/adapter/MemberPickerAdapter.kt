package com.example.teambuilder.ui.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.databinding.ItemMemberPickerBinding
import com.example.teambuilder.util.GenericDiffUtil

class MemberPickerAdapter(
    private inline val onClickRemove: (Player) -> Unit,
    private val teamLeader: Player
) :
    ListAdapter<Player, RecyclerView.ViewHolder>(GenericDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemMemberPickerBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemMemberPickerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Player) {
            with(binding) {
                tvName.text = player.name

                if (player == teamLeader) {
                    tvLeader.text = "리더"
                } else {
                    tvLeader.text = ""
                }

                binding.layPlayer.setOnClickListener {
                    onClickRemove(player)
                }
            }
        }
    }
}