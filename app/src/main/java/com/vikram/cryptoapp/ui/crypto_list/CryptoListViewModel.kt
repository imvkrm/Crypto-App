package com.vikram.cryptoapp.ui.crypto_list

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.vikram.cryptoapp.R
import com.vikram.cryptoapp.data.CryptoRepository
import com.vikram.cryptoapp.data.network.response.CryptoListResponse
import com.vikram.cryptoapp.other.Event
import com.vikram.cryptoapp.other.StatusResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoListViewModel(
    private val cryptoRepository: CryptoRepository
) : ViewModel() {


    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText


    private fun showSnackbarMessage(@StringRes message: Int) {
        _snackbarText.value = Event(message)
    }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _items: LiveData<List<CryptoListResponse>> = fetchDataFromRepository()
    val items: LiveData<List<CryptoListResponse>> = _items

    fun fetchDataFromRepository(): LiveData<List<CryptoListResponse>> {

        _dataLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            cryptoRepository.refreshAllIssuesItems()
        }

        return cryptoRepository.observeAllCryptoItems().switchMap { filterIssues(it) }


    }

    private fun filterIssues(tasksResult: StatusResult<List<CryptoListResponse>>): LiveData<List<CryptoListResponse>> {

        val result = MutableLiveData<List<CryptoListResponse>>()

        if (tasksResult is StatusResult.Success) {

            viewModelScope.launch(Dispatchers.IO) {
                result.postValue(tasksResult.data!!)
                _dataLoading.postValue(false)
            }


        } else {
            _dataLoading.value = false
            result.value = emptyList()
            if (items.value.isNullOrEmpty())
                showSnackbarMessage(R.string.loading_error)
        }

        return result
    }


}