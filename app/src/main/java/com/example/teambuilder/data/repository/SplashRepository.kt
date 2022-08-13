package com.example.teambuilder.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.data_store.KEY_IS_EXIST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SplashRepository @Inject
constructor(
    dataStore: DataStore<Preferences>
) {
    private val matchExistFlow: Flow<Boolean> = dataStore.data.map {
        it[KEY_IS_EXIST] ?: false
    }.flowOn(Dispatchers.IO)

    fun getDataStoreValue() = matchExistFlow
}