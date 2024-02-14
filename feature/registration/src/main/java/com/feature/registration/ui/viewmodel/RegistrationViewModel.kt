package com.feature.registration.ui.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.core.ui.base.BaseViewModel
import com.feature.registration.domain.models.Credentials
import com.feature.registration.domain.usecase.GetCredentialsUseCase
import com.feature.registration.domain.usecase.SaveCredentialsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val getCredentialsUseCase: GetCredentialsUseCase,
    private val saveCredentialsUseCase: SaveCredentialsUseCase
) : BaseViewModel() {

    private val nameRegex = Regex("^[а-яА-Я]+$")
    private var isEmptyCredentials : Boolean = false

    init {
        viewModelScope.launch {
            isEmptyCredentials = getCredentialsUseCase.invoke().isEmpty()
            _registrationState.update { RegistrationState.AlreadyLogged(getCredentialsUseCase.invoke().isEmpty()) }
        }
    }
    private val _registrationState =
        MutableStateFlow<RegistrationState>(RegistrationState.DefaultState)
    val registrationState: StateFlow<RegistrationState> = _registrationState



    private val _userName = MutableStateFlow("")
    private val _surname = MutableStateFlow("")
    private val _phoneNumber = MutableStateFlow("")

    fun isCorrectName(userName: String) {
        _userName.value = userName
        _registrationState.update { RegistrationState.CorrectName(nameRegex.matches(userName)) }
        isButtonActive()
    }

    fun isCorrectSurname(surname: String) {
        _surname.value = surname
        _registrationState.update { RegistrationState.CorrectSurname(nameRegex.matches(surname)) }
        isButtonActive()
    }

    fun isCorrectPhone(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
        isButtonActive()
    }

    private fun isButtonActive() {
        if (_surname.value.matches(nameRegex) && _userName.value.matches(nameRegex) && _phoneNumber.value.length == 10) {
            _registrationState.update { RegistrationState.ButtonActive(true) }
        } else _registrationState.update { RegistrationState.ButtonActive(false) }
    }

    fun saveCredentials() {
        viewModelScope.launch {
            saveCredentialsUseCase(
                Credentials(
                    _userName.value,
                    _surname.value,
                    _phoneNumber.value
                )
            )
        }
    }
    fun isAlreadyLogged() = isEmptyCredentials

}