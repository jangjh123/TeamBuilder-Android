package com.example.teambuilder.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.teambuilder.data.local.MatchDao
import com.example.teambuilder.data.repository.*
import com.example.teambuilder.util.RDBAccessHelper
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
        databaseAccessHelper: RDBAccessHelper
    ) = HomeRepository(dataStore, databaseAccessHelper)

    @ViewModelScoped
    @Provides
    fun provideTeamBuildRepository(
        dataStore: DataStore<Preferences>,
        dao: MatchDao,
        databaseAccessHelper: RDBAccessHelper
    ) = TeamBuildRepository(dataStore, dao, databaseAccessHelper)

    @ViewModelScoped
    @Provides
    fun provideMultipleTeamBuildRepository() = MultipleTeamBuildRepository()

    @ViewModelScoped
    @Provides
    fun provideMatchRepository(
        dataStore: DataStore<Preferences>,
        dao: MatchDao,
        databaseAccessHelper: RDBAccessHelper
    ) = MatchRepository(dataStore, dao, databaseAccessHelper)

    @ViewModelScoped
    @Provides
    fun provideMatchHistory(
        dao: MatchDao
    ) = MatchHistoryRepository(dao)

    @ViewModelScoped
    @Provides
    fun provideRankRepository(
        databaseAccessHelper: RDBAccessHelper
    ) = RankRepository(databaseAccessHelper)
}