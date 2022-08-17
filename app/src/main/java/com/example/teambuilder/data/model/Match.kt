package com.example.teambuilder.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Match(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var winner: String = "None",
    var winnerScore: Int = 0,
    var loserScore: Int = 0,
    val teamAPlayers: String,
    val teamBPlayers: String
)

