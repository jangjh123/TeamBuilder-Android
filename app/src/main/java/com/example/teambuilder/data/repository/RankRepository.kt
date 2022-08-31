package com.example.teambuilder.data.repository

import com.example.teambuilder.util.RDBAccessHelper
import javax.inject.Inject

class RankRepository @Inject constructor(private val rdbAccessHelper: RDBAccessHelper) {
    fun getPlayerForRanking() = rdbAccessHelper.readPlayersForRanking()
}