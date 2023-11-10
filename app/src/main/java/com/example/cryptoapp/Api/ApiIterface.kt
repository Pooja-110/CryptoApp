package com.example.cryptoapp.Api


import com.example.cryptoapp.Models.MarketModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiIterface{
    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    suspend fun getMarketData(): Response<MarketModel>
}