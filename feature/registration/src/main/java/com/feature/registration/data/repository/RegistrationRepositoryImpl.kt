package com.feature.registration.data.repository

import com.core.local.database.CredentialsDao
import com.feature.registration.data.mapper.toCredentials
import com.feature.registration.data.mapper.toCredentialsEntity
import com.feature.registration.domain.models.Credentials
import com.feature.registration.domain.repository.RegistrationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegistrationRepositoryImpl(
    private val credentialsDao: CredentialsDao
) : RegistrationRepository {
    override suspend fun saveCredentials(credentials: Credentials) {

        //need to pass dispatchers to constructor for unit tests
        withContext(Dispatchers.IO) {
            credentialsDao.insertUserCredentials(credentials.toCredentialsEntity())
        }
    }

    override suspend fun getCredentials(): List<Credentials> {
        return withContext(Dispatchers.IO){
            credentialsDao.getCredentials().toCredentials()
        }
    }
}