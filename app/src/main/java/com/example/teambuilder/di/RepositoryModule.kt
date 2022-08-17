package com.example.teambuilder.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.teambuilder.data.local.MatchDao
import com.example.teambuilder.data.repository.MatchRepository
import com.example.teambuilder.data.repository.SplashRepository
import com.example.teambuilder.data.repository.TeamBuildRepository
import com.google.firebase.database.FirebaseDatabase
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
    fun provideSplashRepository(
        dataStore: DataStore<Preferences>
    ) = SplashRepository(dataStore)

    @ViewModelScoped
    @Provides
    fun provideTeamBuildRepository(
        dataStore: DataStore<Preferences>,
        dao: MatchDao,
        realtimeDatabase: FirebaseDatabase
    ) = TeamBuildRepository(dataStore, dao, realtimeDatabase)

    @ViewModelScoped
    @Provides
    fun provideMatchRepository(
        dataStore: DataStore<Preferences>,
        dao: MatchDao,
        realtimeDatabase: FirebaseDatabase
    ) = MatchRepository(dataStore, dao, realtimeDatabase)
}