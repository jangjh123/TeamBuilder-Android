package com.example.teambuilder.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.data_store.KEY_IS_EXIST
import com.example.teambuilder.data.local.MatchDao
import com.example.teambuilder.data.model.Match
import com.example.teambuilder.data.model.Player
import com.google.firebase.database.DatabaseReference
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
    private val realtimeDatabase: DatabaseReference
) {

    suspend fun getAllPlayer(): Flow<ArrayList<Player>> = callbackFlow {
        realtimeDatabase.get().addOnSuccessListener { snapshot ->
            val list = ArrayList<Player>()
            snapshot.children.forEach { player ->
                list.add(
                    Player(
                        name = player.key!!,
                        affiliation = player.child("affiliation").value.toString(),
                        isSuperPlayer = player.child("isSP").getValue<Boolean>()!!
                    )
                )
                launch {
                    trySend(list)
                }
            }
            close()
        }

        realtimeDatabase.get().addOnCanceledListener {
            close()
        }

        realtimeDatabase.get().addOnFailureListener {
            close()
        }

        awaitClose {
            cancel()
        }
    }

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