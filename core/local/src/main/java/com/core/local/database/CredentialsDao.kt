package com.core.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CredentialsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserCredentials(credentials: CredentialsEntity)

    @Query("select * from credentials_db")
    fun getCredentials() : List<CredentialsEntity>

    @Query("DELETE FROM credentials_db")
    fun nukeTable()

}