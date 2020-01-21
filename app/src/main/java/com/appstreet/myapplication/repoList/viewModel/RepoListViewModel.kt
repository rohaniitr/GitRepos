package com.appstreet.myapplication.repoList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appstreet.myapplication.base.BaseViewModel
import com.appstreet.myapplication.remote.ApiClient
import com.appstreet.myapplication.remote.ApiConst
import com.appstreet.myapplication.repoList.model.data.GitRepo
import com.appstreet.myapplication.repoList.model.repo.RepoModel
import io.reactivex.Completable
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
        uiState.value = UiState.PROGRESS
        repoModel.getSavedRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<GitRepo>> {
                override fun onSuccess(repos: List<GitRepo>) {
                    if (!repos.isNullOrEmpty()) {
                        repoList.value = repos
                        uiState.value = UiState.CONTENT
                    }

                    fetchRepoList()
                }

                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onError(e: Throwable) {
                    fetchRepoList()
                }
            })
    }

    fun onRetry() {
        uiState.value = UiState.PROGRESS
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
                    updateSavedRepos(repoModel, value)
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

    private fun updateSavedRepos(repoModel: RepoModel, repos: List<GitRepo>) {
        val deleteRepos = Completable.fromAction { repoModel.deleteAllRepos() }
        val saveRepos = Completable.fromAction { repoModel.saveRepos(repos) }

        deleteRepos.andThen(saveRepos)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
            .let { disposable.add(it) }
    }
}