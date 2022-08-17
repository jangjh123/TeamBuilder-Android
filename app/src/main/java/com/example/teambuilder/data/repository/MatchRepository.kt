package com.example.teambuilder.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.data_store.KEY_IS_EXIST
import com.example.teambuilder.data.local.MatchDao
import com.example.teambuilder.data.model.Match
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

class MatchRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val dao: MatchDao,
    private val realtimeDatabase: FirebaseDatabase
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

    fun setPersonalScore(name: String, score: Int) {
        realtimeDatabase.getReference("PLAYER").child(name).child("personalScore").get().addOnSuccessListener {
            val personalScore = it.getValue<Int>()!!
            realtimeDatabase.getReference("PLAYER").child(name).child("personalScore").setValue(
                personalScore + score
            )
        }
    }
}