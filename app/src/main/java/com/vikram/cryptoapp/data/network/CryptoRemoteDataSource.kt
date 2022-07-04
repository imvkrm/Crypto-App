package com.vikram.cryptoapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vikram.cryptoapp.data.CryptoDataSource
import com.vikram.cryptoapp.data.network.response.CryptoDetailResponse
import com.vikram.cryptoapp.data.network.response.CryptoListResponse
import com.vikram.cryptoapp.other.StatusResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CryptoRemoteDataSource internal constructor(
    private val cryptoApi: CryptoApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CryptoDataSource {

    private val observableTasks = MutableLiveData<StatusResult<List<CryptoListResponse>>>()

    private val observableCryptoDetail = MutableLiveData<StatusResult<CryptoDetailResponse>>()


    override fun observeAllCryptoItems(): LiveData<StatusResult<List<CryptoListResponse>>> {

        return observableTasks
    }

    override suspend fun getAllCryptoItems(): StatusResult<List<CryptoListResponse>> {
        return getAllCrypto()
    }

    override suspend fun refreshAllIssuesItems() {
        observableTasks.postValue(getAllCrypto())
    }

    override fun observeCryptoDetail(): LiveData<StatusResult<CryptoDetailResponse>> {
       return observableCryptoDetail
    }

    override suspend fun getCryptoDetail(symbol: String): StatusResult<CryptoDetailResponse> {
        return getCrypto(symbol)
    }


    override suspend fun refreshCryptoDetail(symbol: String) {
        observableCryptoDetail.postValue(getCrypto(symbol))
    }

    private suspend fun getAllCrypto(): StatusResult<List<CryptoListResponse>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val issuesItems = cryptoApi.getCryptoListFromApi()
                StatusResult.Success(issuesItems)

            } catch (e: Exception) {
                Log.e("Vikram", e.message.toString())

                StatusResult.Error(e)
            }
        }

    private suspend fun getCrypto(symbol: String): StatusResult<CryptoDetailResponse> =
        withContext(ioDispatcher) {
            return@withContext try {
                val issuesItems = cryptoApi.getCryptoDetailFromApi(symbol)
                StatusResult.Success(issuesItems)

            } catch (e: Exception) {
                Log.e("Vikram $symbol", e.message.toString())

                StatusResult.Error(e)
            }
        }




}