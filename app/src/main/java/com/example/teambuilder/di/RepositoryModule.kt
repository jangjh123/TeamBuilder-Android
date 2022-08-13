package com.example.teambuilder.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.teambuilder.data.repository.SplashRepository
import com.example.teambuilder.data.repository.TeamBuildRepository
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
    fun provideTeamBuildRepository(dataStore: DataStore<Preferences>) = TeamBuildRepository(dataStore)

    @ViewModelScoped
    @Provides
    fun provideSplashRepository(dataStore: DataStore<Preferences>) = SplashRepository(dataStore)
}