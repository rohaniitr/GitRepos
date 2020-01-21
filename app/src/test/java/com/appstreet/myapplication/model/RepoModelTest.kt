package com.appstreet.myapplication.model

import com.appstreet.myapplication.base.BaseModelTest
import com.appstreet.myapplication.remote.ApiService
import com.appstreet.myapplication.repoList.model.data.GitRepo
import com.appstreet.myapplication.repoList.model.repo.RepoModel
import com.appstreet.myapplication.util.Constants
import com.google.gson.Gson
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RepoModelTest : BaseModelTest() {
    private val testObserver by lazy { TestObserver<List<GitRepo>>() }
    private val repos by lazy { DataUtil.getRepoList() }

    @Test
    fun repoSuccessTest() {
        val response = MockResponse().setBody(Gson().toJson(repos))
        server.enqueue(response)
        server.start()

        val repoModel = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(server.url("/"))
            .build()
            .let { RepoModel(it) }

        repoModel.getTrendingRepos(Constants.WEEKLY).subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValue(repos)
        testObserver.onComplete()
    }
}