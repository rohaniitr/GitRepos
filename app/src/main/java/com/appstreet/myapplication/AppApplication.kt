package com.appstreet.myapplication

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.appstreet.myapplication.database.DatabaseHelper
import com.appstreet.myapplication.database.DbConstants
import com.appstreet.myapplication.database.RepoDb

class AppApplication : Application() {
    companion object {
        private lateinit var appContext: Context

        fun getInstance(): Context = appContext
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        DatabaseHelper.init(this)
    }
}