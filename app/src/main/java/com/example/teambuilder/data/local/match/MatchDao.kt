package com.example.teambuilder.data.local.match

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.teambuilder.data.model.Match
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    @Query("SELECT * FROM `match`")
    suspend fun getAll(): List<Match>

    @Query("SELECT * FROM 'match' ORDER BY 1 DESC")
    suspend fun getCurrentMatch(): Match

    @Insert
    fun insert(match: Match)

    @Query("UPDATE `match` set winner = 'A'")
    fun setTeamAWinner()

    @Query("UPDATE `match` set winner = 'B'")
    fun setTeamBWinner()
}