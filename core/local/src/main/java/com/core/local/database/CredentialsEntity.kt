package com.core.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "credentials_db")
data class CredentialsEntity(
    val username: String,
    val surname: String,
    @PrimaryKey
    val phone: String
)