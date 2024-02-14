package com.core.local.di

import android.app.Application
import androidx.room.Room
import com.core.local.database.CredentialsDao
import com.core.local.database.CredentialsDatabase
import org.koin.dsl.module


val databaseModule = module {

    fun provideDatabase(application: Application): CredentialsDatabase =
        Room.databaseBuilder(
            application,
            CredentialsDatabase::class.java,
            "credentials_db"
        ).fallbackToDestructiveMigration().build()

    fun provideDao(credentialsDatabase: CredentialsDatabase): CredentialsDao =
        credentialsDatabase.credentialsDao()

    single { provideDatabase(get()) }
    single { provideDao(get()) }
}