package com.appstreet.myapplication.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomVMFactory<T>(val createVm: () -> T) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return createVm() as T
    }

    fun <T : ViewModel?> create(): T {
        return createVm() as T
    }

}