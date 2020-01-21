package com.appstreet.myapplication.dagger

import com.appstreet.myapplication.repoList.viewModel.RepoListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepoListVMModule::class, BaseVMModule::class])
interface RepoListVMComponent {

    fun inject(target: RepoListViewModel)
}