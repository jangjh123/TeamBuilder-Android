package com.example.teambuilder.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.teambuilder.data.local.MatchDao
import javax.inject.Inject

class MatchRepository @Inject constructor(
    dataStore: DataStore<Preferences>,
    private val dao: MatchDao
) {

    suspend fun getMatchFromRoom() = dao.getCurrentMatch()
}