package com.example.teambuilder.di

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
    fun provideTeamBuildRepository() = TeamBuildRepository()
}