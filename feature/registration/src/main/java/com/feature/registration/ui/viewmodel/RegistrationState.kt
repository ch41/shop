package com.feature.registration.ui.viewmodel

sealed class RegistrationState {
    data class CorrectName(val isCorrectName: Boolean = true) : RegistrationState()
    data class CorrectSurname(val isCorrectSurname: Boolean = true) : RegistrationState()
    data class ButtonActive(val isButtonActive: Boolean = false) : RegistrationState()
    data class AlreadyLogged(val isLogged: Boolean = false) : RegistrationState()

    data object DefaultState : RegistrationState()
}