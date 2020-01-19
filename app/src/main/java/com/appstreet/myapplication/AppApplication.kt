package com.appstreet.myapplication

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.appstreet.myapplication.database.DbConstants
import com.appstreet.myapplication.database.RepoDb

class AppApplication : Application() {
    companion object {
        private lateinit var db: RepoDb

        fun getDatabase(): RepoDb = db
    }

    override fun onCreate() {
        super.onCreate()
        //TODO - Shift from mainthread
        db = Room.databaseBuilder(applicationContext, RepoDb::class.java, DbConstants.DB_NAME)
            .allowMainThreadQueries()
            .build()
    }
}