package com.appstreet.myapplication

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.appstreet.myapplication.base.BaseViewModel
import com.appstreet.myapplication.repoList.model.data.GitRepo
import com.appstreet.myapplication.repoList.model.repo.RepoModel
import com.appstreet.myapplication.repoList.viewModel.RepoListViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class RepoListViewModelTest {
    @Mock
    private lateinit var repoModel: RepoModel
    @Mock
    private lateinit var uiState: MutableLiveData<BaseViewModel.UiState>
    @Mock
    private lateinit var repoList: Observer<List<GitRepo>>
    @Mock
    private lateinit var fragment: Fragment

    private val viewModel by lazy {
        ViewModelProviders.of(fragment).get(RepoListViewModel::class.java)
    }

    @Before
    private fun setup() {
        viewModel.getRepoList().observeForever(repoList)
    }

    @Test
    private fun test() {
        when(repoModel.getTrendingRepos("weekly")){}
    }
}