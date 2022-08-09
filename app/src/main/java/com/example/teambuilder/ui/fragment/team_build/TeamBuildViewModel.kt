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

    private fun getPlayers() {
        repository.getAllPlayer { list ->
            _players.postValue(
                list.sortedWith(compareByDescending<Player> { it.isSuperPlayer }.thenBy { it.index })
            )
        }
    }

    init {
        getPlayers()
    }

    private val _players = MutableLiveData<List<Player>>()
    val players: LiveData<List<Player>>
        get() = _players

    var teamA = listOf<Player>()
    var teamB = listOf<Player>()

    private val _aLeader = MutableLiveData<Player>()
    val aLeader: LiveData<Player>
        get() = _aLeader

    private val _bLeader = MutableLiveData<Player>()
    val bLeader: LiveData<Player>
        get() = _bLeader

    private val _isRandom = MutableLiveData<Boolean>()
    val isRandom: LiveData<Boolean>
        get() = _isRandom

    private val _isPicking = MutableLiveData<Boolean>()
    val isPicking: LiveData<Boolean>
        get() = _isPicking

    private val _isSix = MutableLiveData<Boolean>()
    val isSix: LiveData<Boolean>
        get() = _isSix

    private val _isSeven = MutableLiveData<Boolean>()
    val isSeven: LiveData<Boolean>
        get() = _isSeven

    fun setRandom() {
        _isRandom.postValue(true)
        _isPicking.postValue(false)
    }

    fun setPicking() {
        _isPicking.postValue(true)
        _isRandom.postValue(false)
    }

    fun setSix() {
        _isSix.postValue(true)
        _isSeven.postValue(false)
    }

    fun setSeven() {
        _isSeven.postValue(true)
        _isSix.postValue(false)
    }




    fun setALeader(player: Player) {
        aLeader.value.let { // 원래 리더였던 플레이어 팀 초기화
            it?.team = 0
        }
        _aLeader.postValue(player)
        player.team = 1

    }

    fun setBLeader(player: Player) {
        bLeader.value.let { // 원래 리더였던 플레이어 팀 초기화
            it?.team = 0
        }
        _bLeader.postValue(player)
        player.team = 2
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
            if (it != aLeader.value && it != bLeader.value) {
                it.team = 0
            }
        }

        teamA = listOf()
        teamB = listOf()
    }
}