package com.appstreet.myapplication.repoList.model.repo

import android.util.Log
import com.appstreet.myapplication.AppApplication
import com.appstreet.myapplication.database.RepoDb
import com.appstreet.myapplication.remote.ApiService
import com.appstreet.myapplication.repoList.model.data.GitRepo
import retrofit2.Retrofit

class RepoModel(retrofit: Retrofit) {
    private val apiService by lazy { retrofit.create(ApiService::class.java) }
    private val repoDb: RepoDb by lazy { AppApplication.getDatabase() }

    fun getTrendingRepos(since: String) = apiService.getTrendingRepos(since)

    fun saveRepos(repos: List<GitRepo>) = repoDb.gitRepoDao().insertRepos(repos)
    fun getSavedRepos() = repoDb.gitRepoDao().getRepos()
    fun deleteAllRepos() = repoDb.gitRepoDao().deleteAllRepos()
}