package com.example.teambuilder.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.teambuilder.data.model.Match

@Dao
interface MatchDao {
    @Query("SELECT * FROM `match` ORDER BY id DESC")
    suspend fun getAll(): List<Match>

    @Query("SELECT * FROM 'match' ORDER BY 1 DESC")
    suspend fun getCurrentMatch(): Match

    @Insert
    suspend fun insert(match: Match)

    @Update
    suspend fun updateMatch(match: Match)
}