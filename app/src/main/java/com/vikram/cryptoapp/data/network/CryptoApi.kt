package com.vikram.cryptoapp.data.network

import com.vikram.cryptoapp.data.network.response.CryptoDetailResponse
import com.vikram.cryptoapp.data.network.response.CryptoListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoApi {


    @GET("tickers/24hr")
    suspend fun getCryptoListFromApi():List<CryptoListResponse>


    @GET("ticker/24hr")
    suspend fun getCryptoDetailFromApi(@Query("symbol") symbol:String): CryptoDetailResponse

}