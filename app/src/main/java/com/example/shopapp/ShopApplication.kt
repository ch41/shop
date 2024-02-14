package com.example.shopapp

import android.app.Application
import com.core.local.di.databaseModule
import com.core.network.di.networkModule
import com.feature.catalog.di.catalogModule
import com.feature.profile.di.profileModule
import com.feature.registration.di.registrationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShopApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@ShopApplication)
            // App and feature modules
            modules(
                registrationModule,
                catalogModule,
                profileModule

            )
            // Core modules
            modules(
                databaseModule,
                networkModule

            )
        }
    }
}