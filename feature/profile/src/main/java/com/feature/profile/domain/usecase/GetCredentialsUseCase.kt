package com.feature.profile.domain.usecase

import com.feature.profile.domain.repository.ProfileRepository

class GetCredentialsUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke() = repository.getCredentials()
}