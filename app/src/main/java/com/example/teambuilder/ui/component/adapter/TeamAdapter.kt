package com.example.teambuilder.ui.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.data.model.Team
import com.example.teambuilder.databinding.ItemTeamABinding
import com.example.teambuilder.databinding.ItemTeamBBinding

//class TeamAdapter( // ListAdapter 로 변경 예정
//    private val team: Team,
//    private val mList: List<Player>
//) :
//    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (team) {
//            Team.TEAM_A -> {
//                AViewHolder(
//                    ItemTeamABinding.inflate(
//                        LayoutInflater.from(parent.context),
//                        parent,
//                        false
//                    )
//                )
//            }
//            else -> {
//                BViewHolder(
//                    ItemTeamBBinding.inflate(
//                        LayoutInflater.from(parent.context),
//                        parent,
//                        false
//                    )
//                )
//            }
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (team) {
//            Team.TEAM_A -> {
//                (holder as AViewHolder).bind(mList[position])
//            }
//            Team.TEAM_B -> {
//                (holder as BViewHolder).bind(mList[position])
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return mList.size
//    }
//
//    inner class AViewHolder(private val binding: ItemTeamABinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(player: String) {
//            with(binding) {
//                tvName.text = player
//            }
//        }
//    }
//
//    inner class BViewHolder(private val binding: ItemTeamBBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(player: String) {
//            with(binding) {
//                tvName.text = player
//            }
//        }
//    }
//}