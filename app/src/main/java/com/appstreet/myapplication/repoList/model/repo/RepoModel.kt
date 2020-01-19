package com.appstreet.myapplication.repoList.model.repo

import com.appstreet.myapplication.remote.ApiService
import retrofit2.Retrofit

class RepoModel(retrofit: Retrofit) {
    private val apiService by lazy { retrofit.create(ApiService::class.java) }


}