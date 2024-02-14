package com.feature.profile.di

import com.feature.profile.domain.repository.ProfileRepository
import com.feature.profile.data.repository.ProfileRepositoryImpl
import com.feature.profile.domain.usecase.NukeTableUseCase
import com.feature.profile.ui.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val profilePresentationModule = module {
    viewModelOf(::ProfileViewModel)
    singleOf(::ProfileRepositoryImpl) { bind<ProfileRepository>() }

}

val profileDomainModule = module {
    factory { NukeTableUseCase(get()) }
    factory { com.feature.profile.domain.usecase.GetCredentialsUseCase(get()) }
}


val profileModule = module {
    includes(
        profilePresentationModule,
        profileDomainModule
    )
}