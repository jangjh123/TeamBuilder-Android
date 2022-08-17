package com.example.teambuilder.ui.fragment.match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teambuilder.data.model.Match
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.data.repository.MatchRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val repository: MatchRepository
) : ViewModel() {
    private lateinit var match: Match

    private val _teamAScore = MutableLiveData<Int>()
    val teamAScore: LiveData<Int>
        get() = _teamAScore

    private val _teamBScore = MutableLiveData<Int>()
    val teamBScore: LiveData<Int>
        get() = _teamBScore

    fun getCurrentMatch(onResult: (Match) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            match = repository.getMatchFromRoom()
            onResult(match)
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

    fun quitMatch(isLoaded: Boolean, teamA: Array<Player>?, teamB: Array<Player>?) {
        val winner: String
        val winnerScore: Int
        val loserScore: Int

        if (teamAScore.value!! > teamBScore.value!!) {
            winner = "A"
            winnerScore = teamAScore.value!!
            loserScore = teamBScore.value!!
        } else if (
            teamBScore.value!! > teamAScore.value!!) {
            winner = "B"
            winnerScore = teamBScore.value!!
            loserScore = teamAScore.value!!
        } else {
            winner = "Draw"
            winnerScore = teamAScore.value!!
            loserScore = teamBScore.value!!
        }

        if (isLoaded) {
            CoroutineScope(Dispatchers.IO).launch {
                repository.updateMatchResult(
                    match.apply {
                        this.winner = winner
                        this.winnerScore = winnerScore
                        this.loserScore = loserScore
                    }
                )
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val gson = Gson()
                repository.saveMatchResult(
                    Match(
                        0,
                        winner,
                        winnerScore,
                        loserScore,
                        gson.toJson(teamA),
                        gson.toJson(teamB),
                    )
                )
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            repository.setMatchIsOver()
        }
    }

    fun editPersonalScore(name: String, score: Int) {
        repository.setPersonalScore(name, score)
    }
}