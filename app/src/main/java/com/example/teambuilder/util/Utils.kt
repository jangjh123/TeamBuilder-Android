package com.example.teambuilder.util

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