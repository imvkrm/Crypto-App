package com.vikram.cryptoapp.ui.crypto_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vikram.cryptoapp.data.CryptoRepository

@Suppress("UNCHECKED_CAST")
class CryptoDetailViewModelFactory (
    private val cryptoRepository: CryptoRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (CryptoDetailViewModel(cryptoRepository) as T)
}