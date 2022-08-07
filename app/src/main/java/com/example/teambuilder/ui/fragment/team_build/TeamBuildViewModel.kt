package com.example.teambuilder.ui.fragment.team_build

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.data.repository.TeamBuildRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TeamBuildViewModel @Inject constructor(
    private val repository: TeamBuildRepository
) : ViewModel() {
    private val _players = MutableLiveData<List<Player>>()
    val players: LiveData<List<Player>>
        get() = _players

    var teamALeader: Player? = null
    var teamBLeader: Player? = null

    var teamA = listOf<Player>()
    var teamB = listOf<Player>()

    fun getPlayers() {
        repository.getAllPlayer { list ->
            _players.postValue(
                list.sortedWith(compareByDescending<Player> { it.isSuperPlayer }.thenBy { it.index })
            )
        }
    }

    fun setALeader(player: Player) {
        teamALeader = player
    }

    fun setBLeader(player: Player) {
        teamBLeader = player
    }

    fun setATeam(list: List<Player>) {
        teamA = list
        players.value!!.forEach {
            if (it.team == 1 && list.indexOf(it) == -1) {
                it.team = 0
            }
        }
    }

    fun setBTeam(list: List<Player>) {
        teamB = list
        players.value!!.forEach {
            if (it.team == 2 && list.indexOf(it) == -1) {
                it.team = 0
            }
        }
    }

    fun resetTeam() {
        players.value!!.forEach {
            if (it != teamALeader && it != teamBLeader) {
                it.team = 0
            }
        }

        teamA = listOf()
        teamB = listOf()
    }
}