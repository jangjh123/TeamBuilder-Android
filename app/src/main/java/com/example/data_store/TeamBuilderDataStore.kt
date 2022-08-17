package com.example.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "team_builder")
val KEY_IS_EXIST = booleanPreferencesKey("is_exist")
val KEY_TEAM_A_SCORE = intPreferencesKey("team_a_score")
val KEY_TEAM_B_SCORE = intPreferencesKey("team_b_score")