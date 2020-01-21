package com.appstreet.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.appstreet.myapplication.repoList.model.data.GitRepo
import io.reactivex.Single

@Dao
interface RepoDao {
    @Query("SELECT * FROM ${DbConstants.REPO_TABLE}")
    fun getRepos(): Single<List<GitRepo>>

    @Insert
    fun insertRepos(repos: List<GitRepo>)

    @Query("DELETE FROM ${DbConstants.REPO_TABLE}")
    fun deleteAllRepos()
}