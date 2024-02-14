package com.feature.profile.domain.usecase

import com.feature.profile.domain.repository.ProfileRepository

class NukeTableUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(){
        profileRepository.nukeCredentials()
    }
}