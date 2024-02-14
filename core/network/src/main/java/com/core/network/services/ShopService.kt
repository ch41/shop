package com.core.network.services

import com.core.network.models.ListOfProductsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ShopService {

    @GET("https://run.mocky.io/v3/97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getListOfProducts() : ListOfProductsResponse
}