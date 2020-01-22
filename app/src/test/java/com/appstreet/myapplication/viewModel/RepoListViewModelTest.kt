package com.appstreet.myapplication.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.appstreet.myapplication.base.BaseViewModel
import com.appstreet.myapplication.repoList.model.data.GitRepo
import com.appstreet.myapplication.repoList.model.repo.RepoModel
import com.appstreet.myapplication.repoList.viewModel.RepoListViewModel
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*

@RunWith(JUnit4::class)
class RepoListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repoModel: RepoModel
    @Mock
    private lateinit var uiState: Observer<BaseViewModel.UiState>
    @Mock
    private lateinit var repoList: Observer<List<GitRepo>>
    @Mock
    private lateinit var fragment: Fragment

    private val viewModel by lazy {
        RepoListViewModel()
//        ViewModelProviders.of(fragment).get(RepoListViewModel::class.java)
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel.getRepoList().observeForever(repoList)
        viewModel.getUiState().observeForever(uiState)
    }

    @Test
    fun test() {
        Mockito.`when`(repoModel.getTrendingRepos("")).thenAnswer {
            Single.just(ArgumentMatchers.anyList<GitRepo>())
        }
        Assert.assertNotNull(viewModel.getRepoList().value)
    }
}