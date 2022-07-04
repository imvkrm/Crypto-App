package com.vikram.cryptoapp.ui.crypto_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vikram.cryptoapp.data.CryptoRepository

@Suppress("UNCHECKED_CAST")
class CryptoViewModelFactory (
    private val cryptoRepository: CryptoRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (CryptoListViewModel(cryptoRepository) as T)
}