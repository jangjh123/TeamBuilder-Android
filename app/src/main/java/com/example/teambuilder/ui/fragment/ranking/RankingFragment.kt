package com.example.teambuilder.ui.fragment.ranking

import androidx.fragment.app.viewModels
import com.example.teambuilder.R
import com.example.teambuilder.databinding.FragmentRankingBinding
import com.example.teambuilder.ui.BaseFragment
import com.example.teambuilder.ui.component.adapter.MatchHistoryAdapter
import com.example.teambuilder.ui.component.adapter.RankingAdapter
import com.example.teambuilder.ui.component.dialog.DefaultDialog
import com.example.teambuilder.ui.fragment.match_history.MatchHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RankingFragment : BaseFragment<FragmentRankingBinding>(R.layout.fragment_ranking) {
    private val viewModel: RankingViewModel by viewModels()
    private lateinit var adapter: RankingAdapter

    override fun proceed() {
        adapter = RankingAdapter()
        binding.fragment = this@RankingFragment
        binding.adapter = adapter
    }

    override fun setObserver() {
        viewModel.players.onChanged {
            adapter.submitList(it)
        }
    }
}