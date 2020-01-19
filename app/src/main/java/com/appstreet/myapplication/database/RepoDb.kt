package com.appstreet.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appstreet.myapplication.repoList.model.data.GitRepo

@Database(entities = [GitRepo::class], version = 1)
@TypeConverters(UsersConvertor::class)
abstract class RepoDb : RoomDatabase() {
    abstract fun gitRepoDao(): RepoDao
}