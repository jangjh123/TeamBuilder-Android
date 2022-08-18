package com.example.teambuilder.di

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FirebaseDatabaseModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabase() = Firebase.database.getReference("PLAYER")
}