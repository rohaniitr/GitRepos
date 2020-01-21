package com.appstreet.myapplication.repoList.model.repo

import android.util.Log
import com.appstreet.myapplication.AppApplication
import com.appstreet.myapplication.database.RepoDao
import com.appstreet.myapplication.database.RepoDb
import com.appstreet.myapplication.remote.ApiService
import com.appstreet.myapplication.repoList.model.data.GitRepo
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class RepoModel @Inject constructor(
    private val apiService: ApiService,
    private val repoDao: RepoDao
) {

    fun getTrendingRepos(since: String) = apiService.getTrendingRepos(since)

    fun saveRepos(repos: List<GitRepo>) = repoDao.insertRepos(repos)
    fun getSavedRepos() = repoDao.getRepos()
    fun deleteAllRepos() = repoDao.deleteAllRepos()
}