package com.example.teambuilder.ui.fragment.match_history

import androidx.fragment.app.viewModels
import com.example.teambuilder.R
import com.example.teambuilder.databinding.FragmentMatchHistoryBinding
import com.example.teambuilder.ui.BaseFragment
import com.example.teambuilder.ui.component.adapter.MatchHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchHistoryFragment :
    BaseFragment<FragmentMatchHistoryBinding>(R.layout.fragment_match_history) {
    private val viewModel: MatchHistoryViewModel by viewModels()
    private lateinit var adapter: MatchHistoryAdapter

    override fun proceed() {
        adapter = MatchHistoryAdapter()
        binding.fragment = this@MatchHistoryFragment
        binding.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllMatch()
    }

    override fun setObserver() {
        viewModel.matches.onChanged {
            adapter.submitList(it)
        }
    }
}