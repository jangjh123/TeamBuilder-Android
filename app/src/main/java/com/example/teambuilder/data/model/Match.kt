package com.example.teambuilder.data.model

data class Match(
    val winner: String,
    val loser: String,
    val winnerScore: Int,
    val loserScore: Int,
    val teamALeader: Player,
    val teamBLeader: Player,
    val teamAPlayers: List<Player>,
    val teamBPlayers: List<Player>
)
