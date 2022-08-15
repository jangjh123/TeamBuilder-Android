package com.example.teambuilder.di

import android.content.Context
import androidx.room.Room
import com.example.teambuilder.data.local.match.MatchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideTeamDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(
            context,
            MatchDatabase::class.java,
            MatchDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
        .build()


    @Singleton
    @Provides
    fun provideTeamDao(matchDatabase: MatchDatabase) = matchDatabase.getMatchDao()

    @Singleton
    @Provides
    fun provideMatchDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(
            context,
            MatchDatabase::class.java,
            MatchDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
        .build()


    @Singleton
    @Provides
    fun provideMatchDao(matchDatabase: MatchDatabase) = matchDatabase.getMatchDao()

}