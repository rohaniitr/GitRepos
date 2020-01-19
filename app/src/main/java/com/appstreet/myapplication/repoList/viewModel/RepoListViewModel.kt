package com.appstreet.myapplication.repoList.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appstreet.myapplication.base.BaseViewModel
import com.appstreet.myapplication.remote.ApiClient
import com.appstreet.myapplication.remote.ApiConst
import com.appstreet.myapplication.repoList.model.data.GitRepo
import com.appstreet.myapplication.repoList.model.repo.RepoModel
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class RepoListViewModel : BaseViewModel() {
    private val repoList by lazy { MutableLiveData(listOf<GitRepo>()) }
    private val repoModel by lazy { RepoModel(ApiClient(ApiConst.BASE_URL).getApiClient()) }

    fun getRepoList(): LiveData<List<GitRepo>> = repoList

    init {
        if (repoList.value.isNullOrEmpty()) {
            fetchRepoList()
        } else {
            uiState.value = UiState.CONTENT
        }
    }

    fun onRetry() {
        fetchRepoList()
    }

    private fun fetchRepoList() {
        uiState.value = UiState.PROGRESS
        repoModel.getTrendingRepos("weekly")
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<List<GitRepo>> {
                override fun onSuccess(value: List<GitRepo>?) {
                    uiState.postValue(UiState.CONTENT)
                    value?.let {
                        repoList.postValue(it)
                    }
                }

                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onError(e: Throwable?) {
                    if (e is HttpException) {
                        uiState.postValue(UiState.SERVER_ERROR)
                        val statusCode = e.code()
                    } else {
                        uiState.postValue(UiState.NETWORK_ERROR)
                    }
                }
            })
    }
}