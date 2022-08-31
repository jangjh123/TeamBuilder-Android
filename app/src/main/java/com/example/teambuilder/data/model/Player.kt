package com.example.teambuilder.data.model

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player (
    val name: String,
    val affiliation: String,
    val isSuperPlayer: Boolean,
    var team: Team = Team.NONE,
    var isLeader: Boolean = false
) : Parcelable
