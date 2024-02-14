package com.feature.profile.data.repository

import com.core.local.database.CredentialsDao
import com.feature.profile.domain.repository.ProfileRepository
import com.feature.registration.data.mapper.toCredentials
import com.feature.registration.domain.models.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileRepositoryImpl(
    private val credentialsDao: CredentialsDao
) : ProfileRepository {
    override suspend fun getCredentials(): List<Credentials> {
        return withContext(Dispatchers.IO) {
            credentialsDao.getCredentials().toCredentials()
        }
    }

    override suspend fun nukeCredentials() {
        withContext(Dispatchers.IO) {
            credentialsDao.nukeTable()
        }
    }
}