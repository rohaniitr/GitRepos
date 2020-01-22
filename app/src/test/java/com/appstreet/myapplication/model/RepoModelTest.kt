package com.appstreet.myapplication.model

import com.appstreet.myapplication.base.BaseModelTest
import com.appstreet.myapplication.database.RepoDao
import com.appstreet.myapplication.remote.ApiService
import com.appstreet.myapplication.repoList.model.data.GitRepo
import com.appstreet.myapplication.repoList.model.repo.RepoModel
import com.appstreet.myapplication.util.Constants
import com.google.gson.Gson
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RepoModelTest : BaseModelTest() {
    private val testObserver by lazy { TestObserver<List<GitRepo>>() }
    private val repos by lazy { DataUtil.getRepoList() }
    @Mock
    private lateinit var repoDao: RepoDao

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        server.start()
    }

    @Test
    fun repoSuccessTest() {
        val response = MockResponse().setBody(Gson().toJson(repos))
        server.enqueue(response)

        val repoModel = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(server.url("/"))
            .build()
            .create(ApiService::class.java)
            .let { RepoModel(it, repoDao) }

        repoModel.getTrendingRepos(Constants.WEEKLY).subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValue(repos)
        testObserver.onComplete()
    }

    @After
    fun teardown() {
        server.close()
    }
}