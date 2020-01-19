package com.appstreet.myapplication.database

import androidx.room.TypeConverter
import com.appstreet.myapplication.repoList.model.data.GitRepo
import com.appstreet.myapplication.repoList.model.data.User
import com.google.gson.Gson
import com.appstreet.myapplication.util.Extension.fromJson

class UsersConvertor {

    @TypeConverter
    fun fromName(repos: List<User>): String = Gson().toJson(repos)

    @TypeConverter
    fun stringToName(str: String): List<User> = Gson().fromJson<List<User>>(str)
}