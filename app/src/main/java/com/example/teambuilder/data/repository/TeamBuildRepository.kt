package com.example.teambuilder.data.repository

import com.example.teambuilder.data.model.Player
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class TeamBuildRepository {
    private val reference = FirebaseDatabase.getInstance().getReference("PLAYER")

    suspend fun getAllPlayer(): Flow<Player> = callbackFlow {
        reference.get().addOnSuccessListener { snapshot ->
            snapshot.children.forEach { player ->
                launch {
                    send(Player(
                        index = player.child("index").getValue<Int>()!!,
                        name = player.key!!,
                        affiliation = player.child("affiliation").value.toString(),
                        isSuperPlayer = player.child("isSP").getValue<Boolean>()!!
                    ))
                }

            }
        }

        reference.get().addOnCanceledListener {
            close()
        }

        awaitClose()
    }
}