package com.example.sideproject

import android.app.Application
import com.example.sideproject.data.local.LotteryDatabase
import com.example.sideproject.utils.SharePreferenceManager

class App : Application() {

    companion object {
        lateinit var roomDatabase : LotteryDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        SharePreferenceManager.initSharePreferences(this)
        roomDatabase = LotteryDatabase.getDatabase(this)
    }
}