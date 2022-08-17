package com.example.teambuilder.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.data_store.KEY_IS_EXIST
import com.example.teambuilder.data.local.MatchDao
import com.example.teambuilder.data.model.Match
import javax.inject.Inject

class MatchRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val dao: MatchDao
) {

    suspend fun getMatchFromRoom() = dao.getCurrentMatch()

    suspend fun saveMatchResult(match: Match) {
        dao.insert(match)
    }

    suspend fun updateMatchResult(match: Match) {
        dao.updateMatch(match)
    }

    suspend fun setMatchIsOver() {
        dataStore.edit {
            it[KEY_IS_EXIST] = false
        }
    }
}