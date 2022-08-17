package com.example.teambuilder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.teambuilder.data.model.Match

@Database(entities = [Match::class], version = 1, exportSchema = false)
abstract class MatchDatabase : RoomDatabase() {

    abstract fun getMatchDao(): MatchDao

    companion object {
        const val DATABASE_NAME: String = "match_db"
    }
}