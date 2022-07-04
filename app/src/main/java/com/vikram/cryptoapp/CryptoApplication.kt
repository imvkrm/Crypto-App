package com.vikram.cryptoapp

import android.app.Application
import com.vikram.cryptoapp.data.CryptoRepository

class CryptoApplication : Application() {
    val  cryptoRepository: CryptoRepository
        get() = ServiceLocator.provideCryptoRepository(this)
}