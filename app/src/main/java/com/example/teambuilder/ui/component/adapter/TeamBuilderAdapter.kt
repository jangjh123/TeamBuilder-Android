package com.example.teambuilder.ui.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.databinding.ItemTeamBuildBinding

class TeamBuilderAdapter(
    private inline val onClickRemove: (Player) -> Unit,
    private val teamLeader: Player
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mList = ArrayList<Player>()

    fun setList(list: ArrayList<Player>) {
        mList = list
    }

    fun addPlayer(player: Player) {
        mList.add(player)
        this.notifyItemInserted(mList.size - 1)
    }

    fun removePlayer(player: Player) {
        val position = mList.indexOf(player)
        mList.remove(player)
        this.notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTeamBuildBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(private val binding: ItemTeamBuildBinding) :
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