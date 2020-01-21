package com.appstreet.myapplication.dagger

import com.appstreet.myapplication.repoDetail.view.RepoDetailFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CollaboratorModule::class])
interface RepoDetailComponent {

    fun inject(target: RepoDetailFragment)
}