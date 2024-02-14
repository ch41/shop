package com.feature.registration.domain.usecase

import com.feature.registration.domain.models.Credentials
import com.feature.registration.domain.repository.RegistrationRepository

class SaveCredentialsUseCase(
    private val registrationRepository: RegistrationRepository
) {
    suspend operator fun invoke(credentials: Credentials) {
        registrationRepository.saveCredentials(credentials)
    }
}