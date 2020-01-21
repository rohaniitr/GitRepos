package com.appstreet.myapplication.dagger

import com.appstreet.myapplication.repoList.model.data.GitRepo
import com.appstreet.myapplication.repoList.view.RepoListAdapter
import dagger.Module
import dagger.Provides
import kotlin.reflect.KFunction1

@Module
class RepoListModule(
    private val repos: List<GitRepo>,
    private val onClick: KFunction1<@ParameterName(
        name = "repo"
    ) GitRepo, Unit>
) {
    @Provides
    fun providesRepoListAdapter(): RepoListAdapter {
        return RepoListAdapter(repos, onClick)
    }
}