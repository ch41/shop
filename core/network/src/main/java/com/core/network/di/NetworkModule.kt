package com.core.network.di

import com.core.network.services.ShopService

import kotlinx.serialization.json.Json
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideRetrofit() }
}

fun provideRetrofit(): ShopService {
    return Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ShopService::class.java)
}

private const val BASE_URL = "http://185.189.100.107:4000/SL/hs/SLapi/"

