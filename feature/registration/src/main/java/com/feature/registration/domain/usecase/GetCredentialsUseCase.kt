package com.feature.registration.domain.usecase

import com.feature.registration.domain.models.Credentials
import com.feature.registration.domain.repository.RegistrationRepository

class GetCredentialsUseCase(
    private val registrationRepository: RegistrationRepository
) {

    suspend operator fun invoke(): List<Credentials> = registrationRepository.getCredentials()
}