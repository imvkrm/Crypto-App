package com.vikram.cryptoapp.data

import androidx.lifecycle.LiveData
import com.vikram.cryptoapp.data.network.response.CryptoDetailResponse
import com.vikram.cryptoapp.data.network.response.CryptoListResponse
import com.vikram.cryptoapp.other.StatusResult

interface CryptoDataSource {
    fun observeAllCryptoItems(): LiveData<StatusResult<List<CryptoListResponse>>>

    suspend fun getAllCryptoItems(): StatusResult<List<CryptoListResponse>>

    suspend fun refreshAllIssuesItems()

    fun observeCryptoDetail(): LiveData<StatusResult<CryptoDetailResponse>>

    suspend fun getCryptoDetail(symbol: String): StatusResult<CryptoDetailResponse>

    suspend fun refreshCryptoDetail(symbol: String)
}