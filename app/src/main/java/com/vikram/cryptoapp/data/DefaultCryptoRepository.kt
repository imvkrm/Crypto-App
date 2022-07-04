package com.vikram.cryptoapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.vikram.cryptoapp.data.network.response.CryptoDetailResponse
import com.vikram.cryptoapp.data.network.response.CryptoListResponse
import com.vikram.cryptoapp.other.StatusResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultCryptoRepository(
    private val cryptoRemoteDataSource: CryptoDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CryptoRepository {
    override fun observeAllCryptoItems(): LiveData<StatusResult<List<CryptoListResponse>>> {
        return cryptoRemoteDataSource.observeAllCryptoItems()    }

    override suspend fun getAllCryptoItems(): StatusResult<List<CryptoListResponse>> {
        return cryptoRemoteDataSource.getAllCryptoItems()
    }

    override suspend fun refreshAllIssuesItems() {
        cryptoRemoteDataSource.refreshAllIssuesItems()
    }

    override fun observeCryptoDetail(): LiveData<StatusResult<CryptoDetailResponse>> {
        return cryptoRemoteDataSource.observeCryptoDetail()
    }

    override suspend fun getCryptoDetail(symbol: String): StatusResult<CryptoDetailResponse> {
       return cryptoRemoteDataSource.getCryptoDetail(symbol)
    }

    override suspend fun refreshCryptoDetail(symbol: String) {
        cryptoRemoteDataSource.refreshCryptoDetail(symbol)
    }


}