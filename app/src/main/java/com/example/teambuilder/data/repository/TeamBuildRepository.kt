package com.example.teambuilder.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.data_store.KEY_IS_EXIST
import com.example.teambuilder.data.local.MatchDao
import com.example.teambuilder.data.model.Match
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.util.RDBAccessHelper
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TeamBuildRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val dao: MatchDao,
    private val rdbAccessHelper: RDBAccessHelper
) {

    fun getAllPlayer() = rdbAccessHelper.readAllPlayer()


    suspend fun setMatchExist() {
        dataStore.edit {
            it[KEY_IS_EXIST] = true
        }
    }

    suspend fun saveMatch(
        teamAPlayers: String,
        teamBPlayers: String
    ) {
        dao.insert(
            Match(
                teamAPlayers = teamAPlayers,
                teamBPlayers = teamBPlayers,
            )
        )
    }
}