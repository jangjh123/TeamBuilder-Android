package com.example.teambuilder.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.data_store.KEY_IS_EXIST
import com.example.data_store.KEY_TEAM_A_SCORE
import com.example.data_store.KEY_TEAM_B_SCORE
import com.example.teambuilder.util.RDBAccessHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepository @Inject constructor(
    dataStore: DataStore<Preferences>,
    private val rdbAccessHelper: RDBAccessHelper
) {
    private val matchExistFlow: Flow<Boolean> = dataStore.data.map {
        it[KEY_IS_EXIST] ?: false
    }.flowOn(Dispatchers.IO)
    private val teamAScoreFlow: Flow<Int> = dataStore.data.map {
        it[KEY_TEAM_A_SCORE] ?: 0
    }.flowOn(Dispatchers.IO)
    private val teamBScoreFlow: Flow<Int> = dataStore.data.map {
        it[KEY_TEAM_B_SCORE] ?: 0
    }

    fun getMatchExistFlow() = matchExistFlow
    fun getTeamAScoreFlow() = teamAScoreFlow
    fun getTeamBScoreFlow() = teamBScoreFlow

    fun setNewPlayer(playerInformation: Pair<String, String>) {
        rdbAccessHelper.insertNewPlayer(playerInformation)
    }
}