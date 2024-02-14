package com.feature.profile.domain.repository

import com.feature.registration.domain.models.Credentials

interface ProfileRepository {

    suspend fun getCredentials() : List<Credentials>
    suspend fun nukeCredentials()
}