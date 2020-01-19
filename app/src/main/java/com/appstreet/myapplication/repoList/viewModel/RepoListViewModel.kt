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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class RepoListViewModel : BaseViewModel() {
    private val repoList by lazy { MutableLiveData(listOf<GitRepo>()) }
    private val repoModel by lazy { RepoModel(ApiClient(ApiConst.BASE_URL).getApiClient()) }

    fun getRepoList(): LiveData<List<GitRepo>> = repoList

    init {
        val repos = repoModel.getSavedRepos()
        if (!repos.isNullOrEmpty()) {
            repoList.value = repos
            uiState.value = UiState.CONTENT
        } else {
            uiState.value = UiState.PROGRESS
        }

        fetchRepoList()
    }

    fun onRetry() {
        fetchRepoList()
    }

    private fun fetchRepoList() {
        repoModel.getTrendingRepos("weekly")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<GitRepo>> {
                override fun onSuccess(value: List<GitRepo>) {
                    uiState.value = UiState.CONTENT
                    repoList.value = value
                    updateSavedRepos(value)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onError(e: Throwable) {
                    if (!repoList.value.isNullOrEmpty()) {
                        return
                    }

                    if (e is HttpException) {
                        uiState.value = UiState.SERVER_ERROR
                        val statusCode = e.code()
                    } else {
                        uiState.value = UiState.NETWORK_ERROR
                    }
                }
            })
    }

    private fun updateSavedRepos(repos: List<GitRepo>) {
        repoModel.deleteAllRepos()
        repoModel.saveRepos(repos)
    }
}