package com.feature.profile.ui

sealed class ProfileState {
    data class UserCredentials(
        val name: String = "Name",
        val surname: String = "Surname",
        val phoneNumber: String = "+123123123"
    ) : ProfileState()

    data object Exit : ProfileState()
    data object Default : ProfileState()
}