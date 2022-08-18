package com.example.teambuilder.data.repository

import com.example.teambuilder.data.model.RankingPlayer
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RankRepository @Inject constructor(private val realtimeDatabase: DatabaseReference) {
    suspend fun getPlayerForRanking(): Flow<List<RankingPlayer>> = callbackFlow {
        realtimeDatabase.get().addOnSuccessListener { snapshot ->
            val list = ArrayList<RankingPlayer>()
            snapshot.children.forEach { player ->
                list.add(
                    RankingPlayer(
                        name = player.key!!,
                        affiliation = player.child("affiliation").value.toString(),
                        personalScore = player.child("personalScore").getValue<Int>() ?: 0,
                        winCount = player.child("winCount").getValue<Int>() ?: 0,
                        loseCount = player.child("loseCount").getValue<Int>() ?: 0
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
}