package com.example.teambuilder.util

import androidx.lifecycle.LiveData
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.data.model.Team
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object Utils {
    val typePlayerList: Type = object : TypeToken<List<Player>>() {}.type
    
    fun resetTeam(players: List<Player>, teamALeader: Player, teamBLeader: Player) {
        players.forEach {
            if (it != teamALeader && it != teamBLeader) {
                it.team = Team.NONE
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

inline fun <T> ArrayList<T>.isNotEmpty(crossinline isNotEmpty: () -> Unit) {
    if (this.isNotEmpty()) {
        isNotEmpty()
    }
}