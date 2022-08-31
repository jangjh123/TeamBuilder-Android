package com.example.teambuilder.di

import com.example.teambuilder.util.RDBAccessHelper
import com.google.firebase.database.DatabaseReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseAccessHelperModule {

    @Singleton
    @Provides
    fun provideDatabaseAccessHelperModule() = RDBAccessHelper()
}