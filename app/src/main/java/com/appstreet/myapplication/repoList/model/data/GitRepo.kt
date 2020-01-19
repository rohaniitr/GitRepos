package com.appstreet.myapplication.repoList.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appstreet.myapplication.database.DbConstants
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = DbConstants.REPO_TABLE)
data class GitRepo(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val projectId: Long,
    @SerializedName("url")
    val repoUrl: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("languageColor")
    val languageColor: String?,
    @SerializedName("stars")
    val stars: String?,
    @SerializedName("forks")
    val forks: String?,
    @SerializedName("currentPeriodStars")
    val currentPeriodStars: String?,
    @SerializedName("builtBy")
    var collaborators: List<User>?
) : Serializable