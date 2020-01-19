package com.appstreet.myapplication.repoList.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("username")
    var userName: String = "",
    @SerializedName("href")
    var userProfile: String = "",
    @SerializedName("avatar")
    var avatar: String = ""
) : Serializable