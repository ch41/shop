package com.core.local.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CredentialsEntity::class], version = 1, exportSchema = false)
abstract class CredentialsDatabase : RoomDatabase() {
    abstract fun credentialsDao(): CredentialsDao
}