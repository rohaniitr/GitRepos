package com.appstreet.myapplication.dagger

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.appstreet.myapplication.repoList.viewModel.RepoListViewModel
import com.appstreet.myapplication.util.CustomVMFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule(private val fragment: Fragment) {

    @Provides
    fun getRepoViewModel(): RepoListViewModel =
        ViewModelProviders.of(fragment, CustomVMFactory { RepoListViewModel() }).get(
            RepoListViewModel::class.java
        )
}