package com.feature.registration.domain.repository

import com.feature.registration.domain.models.Credentials

interface RegistrationRepository {

    suspend fun saveCredentials(credentials: Credentials)
    suspend fun getCredentials() : List<Credentials>

}