package com.example.sideproject

import android.app.Application
import com.example.sideproject.utils.SharePreferenceManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SharePreferenceManager.initSharePreferences(this)
    }
}