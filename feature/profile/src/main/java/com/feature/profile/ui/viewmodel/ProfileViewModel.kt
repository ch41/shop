package com.feature.profile.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.core.ui.base.BaseViewModel
import com.feature.profile.domain.usecase.GetCredentialsUseCase
import com.feature.profile.domain.usecase.NukeTableUseCase
import com.feature.profile.ui.ProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    val getCredentialsUseCase: GetCredentialsUseCase,
    val nukeTableUseCase: NukeTableUseCase
) : BaseViewModel() {

    init {
        getCredentials()
    }
    private var _profileState = MutableStateFlow<ProfileState>(ProfileState.Default)
    val profileState = _profileState

    fun onButtonExitClick(){
        viewModelScope.launch {
            nukeTableUseCase()
            _profileState.update { ProfileState.Exit }
        }
    }

    private fun getCredentials(){
        viewModelScope.launch {
            val x = getCredentialsUseCase()[0]
            _profileState.update { ProfileState.UserCredentials(x.username,x.surname,x.phoneNumber) }
        }
    }
}