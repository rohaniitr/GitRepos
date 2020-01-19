package com.appstreet.myapplication.repoList.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GitRepo(
    var detailVisible: Boolean = false,
    var id: Long = 0,
    @SerializedName("url")
    var repoUrl: String = "",
    @SerializedName("author")
    var author: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("avatar")
    var avatar: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("language")
    var language: String = "",
    @SerializedName("languageColor")
    var languageColor: String = "",
    @SerializedName("stars")
    var stars: String = "",
    @SerializedName("forks")
    var forks: String = "",
    @SerializedName("currentPeriodStars")
    var currentPeriodStars: String = "",
    @SerializedName("builtBy")
    var collaborators: List<User>
) : Serializable