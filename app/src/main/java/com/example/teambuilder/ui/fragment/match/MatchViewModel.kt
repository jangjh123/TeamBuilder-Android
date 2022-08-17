package com.example.teambuilder.ui.fragment.match

import androidx.lifecycle.ViewModel
import com.example.teambuilder.data.model.Match
import com.example.teambuilder.data.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val repository: MatchRepository
) : ViewModel() {

    fun getCurrentMatch(onResult: (Match) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            onResult(repository.getMatchFromRoom())
        }
    }
}