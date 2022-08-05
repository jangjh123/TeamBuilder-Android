package com.example.teambuilder.data.model

data class Player(
    val index: Int,
    val name: String,
    val affiliation: String,
    val isSuperPlayer: Boolean,
    var team: Int = 0,
)
