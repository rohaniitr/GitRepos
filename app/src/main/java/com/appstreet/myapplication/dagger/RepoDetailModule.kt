package com.appstreet.myapplication.dagger

import com.appstreet.myapplication.repoDetail.view.CollaboratorAdapter
import com.appstreet.myapplication.repoList.model.data.User
import dagger.Module
import dagger.Provides
import kotlin.reflect.KFunction1

@Module
class CollaboratorModule(
    private val users: List<User>,
    private val onClick: KFunction1<@ParameterName(
        name = "user"
    ) User, Unit>
) {
    @Provides
    fun providesRepoListAdapter(): CollaboratorAdapter {
        return CollaboratorAdapter(users, onClick)
    }
}