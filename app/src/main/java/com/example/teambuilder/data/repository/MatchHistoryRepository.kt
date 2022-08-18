package com.example.teambuilder.data.repository

import com.example.teambuilder.data.local.MatchDao
import javax.inject.Inject

class MatchHistoryRepository @Inject constructor(
    private val dao: MatchDao
) {

    suspend fun getAllMatchFromRoom() = dao.getAll()
}