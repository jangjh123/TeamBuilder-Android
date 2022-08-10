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
    RecyclerView.Adapter<MemberPickerAdapter.ViewHolder>() {
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
            ItemMemberPickerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
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