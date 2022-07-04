package com.vikram.cryptoapp.ui.crypto_detail

import android.util.Log
import androidx.lifecycle.*
import com.vikram.cryptoapp.data.CryptoRepository
import com.vikram.cryptoapp.data.network.response.CryptoDetailResponse
import com.vikram.cryptoapp.other.StatusResult
import kotlinx.coroutines.launch

class CryptoDetailViewModel(
    private val cryptoRepository: CryptoRepository
) : ViewModel() {

    private val _symbol = MutableLiveData<String>()

    fun start(symbol: String) {

        if (_dataLoading.value == true || symbol == _symbol.value) {
            return
        }
        _symbol.value = symbol

    }



    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading


    val cryptoItems: LiveData<CryptoDetailResponse>
        get() = _cryptoItems

    private var _cryptoItems = _symbol.switchMap { symbol ->
        fetchDataFromRepository(symbol)
    }

    private fun fetchDataFromRepository(symbol: String): LiveData<CryptoDetailResponse> {
        Log.e("Vikram1", symbol)

        _dataLoading.value = true
         viewModelScope.launch {
            cryptoRepository.refreshCryptoDetail(symbol)

        }


        return cryptoRepository.observeCryptoDetail()!!.switchMap { filterComments(it) }

    }

    private fun filterComments(issuesResult: StatusResult<CryptoDetailResponse>?): LiveData<CryptoDetailResponse> {

        val result = MutableLiveData<CryptoDetailResponse>()

        if (issuesResult is StatusResult.Success) {

            viewModelScope.launch {
                result.value = issuesResult.data!!
                _dataLoading.value = false
            }

        } else {
            result.value = CryptoDetailResponse()
            _dataLoading.value = false
        }


        return result
    }

}