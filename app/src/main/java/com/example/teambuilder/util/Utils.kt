package com.example.teambuilder.util

import androidx.lifecycle.LiveData
import com.example.teambuilder.data.model.Player

object Utils {
    fun resetTeam(players: List<Player>, teamALeader: Player, teamBLeader: Player) {
        players.forEach {
            if (it != teamALeader && it != teamBLeader) {
                it.team = 0
            }
        }
    }
}

inline fun LiveData<Boolean>.isNullOrFalse(crossinline isNullOrFalse: () -> Unit) {
    if (this.value == false) {
        isNullOrFalse()
    } else if (this.value == null) {
        isNullOrFalse()
    }
}

inline fun LiveData<Boolean>.isTrue(crossinline isTrue: () -> Unit) {
    if (this.value == true) {
        isTrue()
    }
}