package com.example.teambuilder.util

import com.example.teambuilder.data.model.Player
import com.example.teambuilder.data.model.RankingPlayer
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class RDBAccessHelper {
    private val rdb = Firebase.database.getReference("PLAYER")

    fun readAllPlayer(): Flow<ArrayList<Player>> = callbackFlow {
        rdb.get().addOnSuccessListener { snapshot ->
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

        rdb.get().addOnCanceledListener {
            close()
        }

        rdb.get().addOnFailureListener {
            close()
        }

        awaitClose {
            cancel()
        }
    }

    fun readPlayersForRanking(): Flow<List<RankingPlayer>> = callbackFlow {
        rdb.get().addOnSuccessListener { snapshot ->
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

        rdb.get().addOnCanceledListener {
            close()
        }

        rdb.get().addOnFailureListener {
            close()
        }

        awaitClose {
            cancel()
        }
    }

    fun updatePersonalScore(name: String, score: Int) {
        rdb.child(name).child("personalScore").get().addOnSuccessListener {
            val personalScore = it.getValue<Int>() ?: 0
            rdb.child(name).child("personalScore").setValue(
                personalScore + score
            )
        }
    }

    fun updatePersonalMatchCount(isWin: Boolean, players: List<Player>) {
        players.forEach {
            rdb.child(it.name).child(
                if (isWin) {
                    "winCount"
                } else {
                    "loseCount"
                }
            ).get().addOnSuccessListener { snapshot ->
                val winCount = snapshot.getValue<Int>() ?: 0
                rdb.child(it.name).child(
                    if (isWin) {
                        "winCount"
                    } else {
                        "loseCount"
                    }
                ).setValue(winCount + 1)
            }
        }
    }

    fun insertNewPlayer(playerInformation: Pair<String, String>) {
        rdb.child(playerInformation.first).run {
            child("affiliation").setValue(playerInformation.second)
            child("isSP").setValue(false)
        }
    }
}