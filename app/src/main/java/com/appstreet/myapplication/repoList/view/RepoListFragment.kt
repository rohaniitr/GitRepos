package com.appstreet.myapplication.repoList.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.appstreet.myapplication.R
import com.appstreet.myapplication.base.BaseFragment
import com.appstreet.myapplication.base.BaseViewModel
import com.appstreet.myapplication.repoList.model.data.GitRepo
import com.appstreet.myapplication.repoList.viewModel.RepoListViewModel
import kotlinx.android.synthetic.main.fragment_repo_list.*

class RepoListFragment : BaseFragment() {
    override fun getLayoutId() = R.layout.fragment_repo_list
    private val viewModel by lazy { ViewModelProviders.of(this).get(RepoListViewModel::class.java) }
    private val repoList by lazy { mutableListOf<GitRepo>() }
    private val adapter by lazy { RepoListAdapter(repoList, ::onClick) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setObservers()
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
        //Launch Repo Detail Fragment
        Toast.makeText(context, "${repo.author}", Toast.LENGTH_SHORT).show()
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