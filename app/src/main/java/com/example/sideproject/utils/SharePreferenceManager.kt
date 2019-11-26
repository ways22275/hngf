package com.example.sideproject.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.sideproject.utils.Constant.SHARED_KEY_TOKEN
import com.example.sideproject.utils.Constant.SHARE_PREFERENCE_KEY

object SharePreferenceManager {

    private var mSharedPreferences: SharedPreferences? = null

    fun initSharePreferences (application: Application) {
        mSharedPreferences = application.getSharedPreferences(SHARE_PREFERENCE_KEY, Context.MODE_PRIVATE)
    }

    fun putToken(string : String)
            = mSharedPreferences!!.edit().putString(SHARED_KEY_TOKEN, string).apply()

    fun getToken() : String
            = mSharedPreferences!!.getString(SHARED_KEY_TOKEN, "")!!
}