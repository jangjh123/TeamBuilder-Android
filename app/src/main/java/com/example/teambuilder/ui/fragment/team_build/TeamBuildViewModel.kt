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
}