package com.example.teambuilder.ui.component.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teambuilder.R
import com.example.teambuilder.data.model.RankingPlayer
import com.example.teambuilder.databinding.ItemRankingBinding
import com.example.teambuilder.util.GenericDiffUtil
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class RankingAdapter(
) : ListAdapter<RankingPlayer, RecyclerView.ViewHolder>(GenericDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemRankingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemRankingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ranking: RankingPlayer) {
            with(binding) {
                tvName.text = ranking.name
                tvAffiliation.text = ranking.affiliation
                civAffiliation.setImageResource(
                    when (ranking.affiliation) {
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

                tvGoalCount.text = "${ranking.personalScore}"
                tvWinCount.text = "${ranking.winCount}"
                tvLoseCount.text = "${ranking.loseCount}"

                val pieEntry = listOf(
                    PieEntry(ranking.winCount.toFloat(), "승"),
                    PieEntry(ranking.loseCount.toFloat(), "패")
                )

                val pieData = PieData(PieDataSet(pieEntry, ""))

                pieChart.apply {
                    setTouchEnabled(false)
                    data = pieData
                    data.setValueTextColor(Color.TRANSPARENT)
                    data.dataSet.colors.apply {
                        add(0, ContextCompat.getColor(root.context, R.color.point_color))
                        add(1, ContextCompat.getColor(root.context, R.color.light_gray))
                        setUsePercentValues(true)
                        setDrawEntryLabels(false)
                        description.isEnabled = false
                        legend.isEnabled = false
                        isDrawHoleEnabled = false
                        requestLayout()
                    }
                }
            }
        }
    }
}