package com.example.teambuilder.ui.fragment.match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _teamAScore = MutableLiveData<Int>()
    val teamAScore: LiveData<Int>
        get() = _teamAScore

    private val _teamBScore = MutableLiveData<Int>()
    val teamBScore: LiveData<Int>
        get() = _teamBScore

    fun getCurrentMatch(onResult: (Match) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            onResult(repository.getMatchFromRoom())
        }
    }

    init {
        _teamAScore.postValue(0)
        _teamBScore.postValue(0)
    }

    fun setTeamAScore(score: Int) {
        _teamAScore.postValue(teamAScore.value!! + score)
    }

    fun setTeamBScore(score: Int) {
        _teamBScore.postValue(teamBScore.value!! + score)
    }
}