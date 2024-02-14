package com.feature.registration.di

import com.feature.registration.domain.repository.RegistrationRepository
import com.feature.registration.data.repository.RegistrationRepositoryImpl
import com.feature.registration.domain.usecase.GetCredentialsUseCase
import com.feature.registration.domain.usecase.SaveCredentialsUseCase
import com.feature.registration.ui.viewmodel.RegistrationViewModel
import org.koin.core.module.dsl.bind
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val registrationPresentationModule = module {
    viewModelOf(::RegistrationViewModel)
    singleOf(::RegistrationRepositoryImpl) { bind<RegistrationRepository>() }
}

val registrationDomainModule = module {
    factory { SaveCredentialsUseCase(get()) }
    factory { GetCredentialsUseCase(get()) }
}

val registrationModule = module {
    includes(
        registrationPresentationModule,
        registrationDomainModule
    )
}