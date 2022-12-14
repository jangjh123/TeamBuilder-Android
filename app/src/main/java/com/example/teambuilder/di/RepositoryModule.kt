package com.example.teambuilder.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.teambuilder.data.local.MatchDao
import com.example.teambuilder.data.repository.*
import com.google.firebase.database.DatabaseReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideHomeRepository(
        dataStore: DataStore<Preferences>,
        realtimeDatabase: DatabaseReference
    ) = HomeRepository(dataStore, realtimeDatabase)

    @ViewModelScoped
    @Provides
    fun provideTeamBuildRepository(
        dataStore: DataStore<Preferences>,
        dao: MatchDao,
        realtimeDatabase: DatabaseReference
    ) = TeamBuildRepository(dataStore, dao, realtimeDatabase)

    @ViewModelScoped
    @Provides
    fun provideMatchRepository(
        dataStore: DataStore<Preferences>,
        dao: MatchDao,
        realtimeDatabase: DatabaseReference
    ) = MatchRepository(dataStore, dao, realtimeDatabase)

    @ViewModelScoped
    @Provides
    fun provideMatchHistory(
        dao: MatchDao
    ) = MatchHistoryRepository(dao)

    @ViewModelScoped
    @Provides
    fun provideRankRepository(
        realtimeDatabase: DatabaseReference
    ) = RankRepository(realtimeDatabase)
}