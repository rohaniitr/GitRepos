package com.appstreet.myapplication.repoList.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.appstreet.myapplication.R
import com.appstreet.myapplication.base.BaseFragment
import com.appstreet.myapplication.base.BaseViewModel
import com.appstreet.myapplication.dagger.DaggerRepoListComponent
import com.appstreet.myapplication.dagger.RepoListModule
import com.appstreet.myapplication.dagger.RepoListVMModule
import com.appstreet.myapplication.dagger.ViewModelModule
import com.appstreet.myapplication.remote.ApiConst
import com.appstreet.myapplication.repoDetail.view.RepoDetailFragment
import com.appstreet.myapplication.repoList.model.data.GitRepo
import com.appstreet.myapplication.repoList.viewModel.RepoListViewModel
import kotlinx.android.synthetic.main.fragment_repo_list.*
import javax.inject.Inject

class RepoListFragment : BaseFragment() {
    override fun getLayoutId() = R.layout.fragment_repo_list
    @Inject
    lateinit var viewModel: RepoListViewModel
    private val repoList by lazy { mutableListOf<GitRepo>() }
    @Inject
    lateinit var adapter: RepoListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDagger()
        setRecyclerView()
        setObservers()
    }

    private fun initDagger() {
        DaggerRepoListComponent.builder()
            .repoListModule(RepoListModule(repoList, ::onClick))
            .viewModelModule(ViewModelModule(this))
            .build()
            .inject(this)
    }

    private fun setObservers() {
        viewModel.getRepoList().observe(this, Observer<List<GitRepo>> {
            if (!isAdded) {
                return@Observer
            }
            if (!it.isNullOrEmpty()) {
                updateRecyclerView(it)
            }
        })
        viewModel.getUiState().observe(this,
            Observer<BaseViewModel.UiState> { uiState ->
                if (uiState == null || !isAdded) {
                    return@Observer
                }

                when (uiState) {
                    BaseViewModel.UiState.PROGRESS -> showProgressView()
                    BaseViewModel.UiState.CONTENT -> showContentView()
                    BaseViewModel.UiState.NETWORK_ERROR ->
                        showNetworkErrorView(View.OnClickListener { viewModel.onRetry() })
                    BaseViewModel.UiState.SERVER_ERROR ->
                        showServerErrorView(View.OnClickListener { viewModel.onRetry() })
                }
            })
    }

    private fun onClick(repo: GitRepo) {
        findNavController().navigate(
            R.id.action_repoListFragment_to_repoDetailFragment,
            RepoDetailFragment.getBundle(repo)
        )
    }

    private fun updateRecyclerView(repos: List<GitRepo>) {
        repoList.clear()
        repoList.addAll(repos)
        adapter.notifyDataSetChanged()
    }

    private fun setRecyclerView() {
        repo_recycler_view.adapter = adapter
        repo_recycler_view.layoutManager = LinearLayoutManager(context)

        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        repo_recycler_view.addItemDecoration(decoration)
    }
}