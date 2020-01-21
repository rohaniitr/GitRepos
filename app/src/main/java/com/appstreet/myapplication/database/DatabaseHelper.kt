package com.appstreet.myapplication.database

import android.content.Context
import androidx.room.Room

object DatabaseHelper {
    private lateinit var repoDb: RepoDb

    fun getRepoDatabase() = repoDb

    fun init(appContext: Context) {
        repoDb = Room.databaseBuilder(appContext, RepoDb::class.java, DbConstants.DB_NAME)
            .build()
    }
}