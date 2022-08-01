package com.example.teambuilder.data.repository

import com.example.teambuilder.data.model.Player
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class TeamBuildRepository {
    private val reference = FirebaseDatabase.getInstance().getReference("PLAYER")

    fun getAllPlayer(onResult: (List<Player>) -> Unit) {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val players = mutableListOf<Player>()
                snapshot.children.forEach {
                    players.add(
                        Player(
                            name = it.key!!,
                            affiliation = it.child("affiliation").value.toString(),
                            isSuperPlayer = it.child("isSP").getValue<Boolean>()!!
                        )
                    )
                }

                onResult(players)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}