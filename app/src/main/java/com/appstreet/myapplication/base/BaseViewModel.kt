package com.appstreet.myapplication.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    var uiState: MutableLiveData<UiState> = MutableLiveData(UiState.CONTENT)

    enum class UiState {
        PROGRESS,
        CONTENT,
        SERVER_ERROR,
        NETWORK_ERROR
    }
}