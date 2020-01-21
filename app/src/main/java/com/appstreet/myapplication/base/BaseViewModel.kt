package com.appstreet.myapplication.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val uiState: MutableLiveData<UiState> by lazy { MutableLiveData(UiState.CONTENT) }
    protected val disposable by lazy { CompositeDisposable() }

    fun getUiState(): LiveData<UiState> = uiState

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    enum class UiState {
        PROGRESS,
        CONTENT,
        SERVER_ERROR,
        NETWORK_ERROR
    }
}