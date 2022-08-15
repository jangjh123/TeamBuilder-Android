package com.example.teambuilder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Match(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val winner: String,
    val winnerScore: Int,
    val loserScore: Int,
    val teamALeaderIdx: Int,
    val teamBLeaderIdx: Int,
    val teamAPlayers: String,
    val teamBPlayers: String
)
