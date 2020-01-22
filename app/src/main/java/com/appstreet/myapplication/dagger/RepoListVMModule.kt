package com.appstreet.myapplication.dagger

import com.appstreet.myapplication.database.DatabaseHelper
import com.appstreet.myapplication.remote.ApiService
import com.appstreet.myapplication.repoList.model.repo.RepoModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RepoListVMModule(private val baseUrl: String) {

    @Provides
    fun getRepoModel() = RepoModel(
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java),
        DatabaseHelper.getRepoDatabase().gitRepoDao()
    )
}