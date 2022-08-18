package com.example.teambuilder.ui.fragment.ranking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teambuilder.data.model.RankingPlayer
import com.example.teambuilder.data.repository.RankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val repository: RankRepository
) : ViewModel() {

    private val _players = MutableLiveData<List<RankingPlayer>>()
    val players: LiveData<List<RankingPlayer>>
        get() = _players

    init {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getPlayerForRanking().collect {
                _players.postValue(it)
            }
        }
    }
}