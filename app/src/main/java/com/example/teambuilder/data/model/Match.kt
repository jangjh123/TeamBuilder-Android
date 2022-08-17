package com.example.teambuilder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Match(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val winner: String = "None",
    val winnerScore: Int = 0,
    val loserScore: Int = 0,
    val teamAPlayers: String,
    val teamBPlayers: String
)
