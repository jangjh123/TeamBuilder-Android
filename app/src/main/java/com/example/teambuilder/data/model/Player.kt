package com.example.teambuilder.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player (
    val index: Int,
    val name: String,
    val affiliation: String,
    val isSuperPlayer: Boolean,
    var team: Team = Team.NONE,
    var isLeader: Boolean = false
) : Parcelable
