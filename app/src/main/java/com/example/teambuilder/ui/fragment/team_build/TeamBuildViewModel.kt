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

    private lateinit var aLeader: Player
    private lateinit var bLeader: Player

    fun getPlayers() {
        repository.getAllPlayer {
            _players.postValue(it)
        }
    }

    fun setALeader(player: Player) {
        aLeader = player
    }

    fun setBLeader(player: Player) {
        bLeader = player
    }
}