package com.example.teambuilder.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.data_store.KEY_IS_EXIST
import com.example.data_store.KEY_TEAM_A_SCORE
import com.example.data_store.KEY_TEAM_B_SCORE
import com.example.teambuilder.data.local.MatchDao
import com.example.teambuilder.data.model.Match
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.util.Utils.typePlayerList
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MatchRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val dao: MatchDao,
    private val realtimeDatabase: DatabaseReference
) {
    private val teamAScoreFlow: Flow<Int> = dataStore.data.map {
        it[KEY_TEAM_A_SCORE] ?: 0
    }
    private val teamBScoreFlow: Flow<Int> = dataStore.data.map {
        it[KEY_TEAM_B_SCORE] ?: 0
    }

    fun getTeamAScoreFlow() = teamAScoreFlow
    fun getTeamBScoreFlow() = teamBScoreFlow

    suspend fun getMatchFromRoom() = dao.getCurrentMatch()

    suspend fun updateMatchResult(match: Match) {
        dao.updateMatch(match)
    }

    suspend fun setMatchIsOver() {
        dataStore.edit {
            it[KEY_IS_EXIST] = false
        }
    }

    fun setPersonalScore(name: String, score: Int) {
        realtimeDatabase.child(name).child("personalScore").get()
            .addOnSuccessListener {
                val personalScore = it.getValue<Int>()!!
                realtimeDatabase.child(name).child("personalScore").setValue(
                    personalScore + score
                )
            }
    }

    fun setPersonalMatchCount(isWin: Boolean, json: String) {
        val gson = Gson()
        val players: List<Player> = gson.fromJson(json, typePlayerList)

        players.forEach {
            realtimeDatabase.child(it.name).child(
                if (isWin) {
                    "winCount"
                } else {
                    "loseCount"
                }
            ).get()
                .addOnSuccessListener { snapshot ->
                    val winCount = snapshot.getValue<Int>() ?: 0
                    realtimeDatabase.child(it.name).child(
                        if (isWin) {
                            "winCount"
                        } else {
                            "loseCount"
                        }
                    ).setValue(
                        winCount + 1
                    )
                }
        }
    }

    suspend fun saveTeamAScoreIntoDataStore(score: Int) {
        dataStore.edit {
            it[KEY_TEAM_A_SCORE] = score
        }
    }

    suspend fun saveTeamBScoreIntoDataStore(score: Int) {
        dataStore.edit {
            it[KEY_TEAM_B_SCORE] = score
        }
    }

    suspend fun setScoreToZero() {
        dataStore.edit {
            it[KEY_TEAM_A_SCORE] = 0
            it[KEY_TEAM_B_SCORE] = 0
        }
    }
}