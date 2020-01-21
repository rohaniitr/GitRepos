package com.appstreet.myapplication.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class BaseVMModule {

    @Provides
    fun compositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}