package com.example.teambuilder.data.local

import androidx.room.TypeConverter
import com.example.teambuilder.data.model.Player
import com.google.gson.Gson

class TeamBuilderTypeConverter {
    @TypeConverter
    fun fromStringList(value: List<Player>?): String = Gson().toJson(value)
}