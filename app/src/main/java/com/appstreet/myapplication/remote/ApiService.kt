package com.appstreet.myapplication.remote

import com.appstreet.myapplication.repoList.model.data.GitRepo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(ApiConst.REPO_LIST)
    fun getTrendingRepos(@Query("since") since: String): Single<List<GitRepo>>
}