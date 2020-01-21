package com.appstreet.myapplication.dagger

import com.appstreet.myapplication.repoList.view.RepoListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepoListModule::class])
interface RepoListComponent {

    fun inject(target: RepoListFragment)
}