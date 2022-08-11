package com.example.teambuilder.ui.fragment.team_build

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.data.model.Team
import com.example.teambuilder.data.repository.TeamBuildRepository
import com.example.teambuilder.util.SingleLiveEvent
import com.example.teambuilder.util.isTrue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamBuildViewModel @Inject constructor(
    private val repository: TeamBuildRepository
) : ViewModel() {

    var players = ArrayList<Player>()

    suspend fun getPlayers() {
        repository.getAllPlayer()
            .catch { errorInfo.call() }
            .collect {
            players = it
        }
        players.sortWith(compareByDescending<Player> { it.isSuperPlayer }.thenBy { it.name })
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getPlayers()
        }
    }

    private val _teamALeader = MutableLiveData<Player>()
    val teamALeader: LiveData<Player>
        get() = _teamALeader

    private val _teamBLeader = MutableLiveData<Player>()
    val teamBLeader: LiveData<Player>
        get() = _teamBLeader

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

    private val _isSelectingMethodVisible = MutableLiveData<Boolean>()
    val isSelectingMethodVisible: LiveData<Boolean>
        get() = _isSelectingMethodVisible

    private val _isMemberCountVisible = MutableLiveData<Boolean>()
    val isMemberCountVisible: LiveData<Boolean>
        get() = _isMemberCountVisible

    private val _isBuildButtonVisible = MutableLiveData<Boolean>()
    val isBuildButtonVisible: LiveData<Boolean>
        get() = _isBuildButtonVisible

    private val _isConfirmButtonAvailable = MutableLiveData<Boolean>()
    val isConfirmButtonAvailable: LiveData<Boolean>
        get() = _isConfirmButtonAvailable

    val errorInfo = SingleLiveEvent<Unit>()

    fun setRandom(boolean: Boolean) {
        if (boolean) {
            isPicking.isTrue {
                _isPicking.postValue(false)
            }
            _isRandom.postValue(true)
        } else {
            _isRandom.postValue(false)
        }
    }

    fun setPicking(boolean: Boolean) {
        if (boolean) {
            isRandom.isTrue {
                _isRandom.postValue(false)
            }
            _isPicking.postValue(true)
        } else {
            _isPicking.postValue(false)
        }
    }

    fun setSix(boolean: Boolean) {
        if (boolean) {
            isSeven.isTrue {
                _isSeven.postValue(false)
            }
            _isSix.postValue(true)
        } else {
            _isSix.postValue(false)
        }
    }

    fun setSeven(boolean: Boolean) {
        if (boolean) {
            isSix.isTrue {
                _isSix.postValue(false)
            }
            _isSeven.postValue(true)
        } else {
            _isSeven.postValue(false)
        }
    }

    fun setSelectingMethodVisible(boolean: Boolean) {
        _isSelectingMethodVisible.postValue(boolean)
    }

    fun setMemberCountVisible(boolean: Boolean) {
        _isMemberCountVisible.postValue(boolean)
    }

    fun setBuildButtonVisible(boolean: Boolean) {
        _isBuildButtonVisible.postValue(boolean)
    }

    fun setConfirmButtonAvailable(boolean: Boolean) {
        _isConfirmButtonAvailable.postValue(boolean)
    }

    fun setTeamALeader(player: Player) {
        teamALeader.value.let { // 원래 리더였던 플레이어 팀 초기화
            it?.team = Team.NONE
            it?.isLeader = false
        }
        _teamALeader.postValue(player)
        player.team = Team.TEAM_A
        player.isLeader = true

    }

    fun setTeamBLeader(player: Player) {
        teamBLeader.value.let { // 원래 리더였던 플레이어 팀 초기화
            it?.team = Team.NONE
            it?.isLeader = false
        }
        _teamBLeader.postValue(player)
        player.team = Team.TEAM_B
        player.isLeader = true
    }
}