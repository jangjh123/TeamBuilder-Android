package com.example.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "teamBuilder")
val KEY_IS_REGISTERED = booleanPreferencesKey("isRegistered")
val KEY_GROUP_A_NAME = stringPreferencesKey("groupAName")
val KEY_GROUP_B_NAME = stringPreferencesKey("groupBName")