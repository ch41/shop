package com.feature.catalog.di

import com.core.network.di.networkModule
import com.feature.catalog.domain.CatalogRepository
import com.feature.catalog.data.repository.CatalogRepositoryImpl
import com.feature.catalog.domain.usecase.GetItemListUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.feature.catalog.ui.viewmodel.CatalogViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf

val catalogModule = module {
    viewModelOf(::CatalogViewModel)
    singleOf(::CatalogRepositoryImpl) { bind<CatalogRepository>() }
    factory { GetItemListUseCase(get()) }
    includes(
        networkModule
    )

}