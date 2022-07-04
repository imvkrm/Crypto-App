package com.vikram.cryptoapp

import android.content.Context
import android.util.Log
import androidx.annotation.VisibleForTesting
import com.vikram.cryptoapp.data.CryptoDataSource
import com.vikram.cryptoapp.data.CryptoRepository
import com.vikram.cryptoapp.data.DefaultCryptoRepository
import com.vikram.cryptoapp.data.network.CryptoApi
import com.vikram.cryptoapp.data.network.CryptoRemoteDataSource
import com.vikram.cryptoapp.other.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

    private val lock = Any()
    private var cryptoApi: CryptoApi?=null

    @Volatile
    var cryptoRepository: CryptoRepository? = null
        @VisibleForTesting set

    fun provideCryptoRepository(context: Context): CryptoRepository {
        synchronized(this) {
            return cryptoRepository ?: createCryptoRepository(context)
        }
    }

    private fun createCryptoRepository(context: Context): CryptoRepository {
        val newRepo =
            DefaultCryptoRepository(createCryptoRemoteDataSource())
        cryptoRepository = newRepo
        return newRepo
    }


    private fun createCryptoRemoteDataSource(): CryptoDataSource {

        val cryptosApi = cryptoApi ?: createCryptoApi()

        return CryptoRemoteDataSource(cryptosApi)
    }

    private fun createCryptoApi(): CryptoApi {
        val result = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(CryptoApi::class.java)

        cryptoApi = result

        return result
    }
}