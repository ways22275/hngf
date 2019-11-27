package com.example.sideproject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sideproject.R
import com.example.sideproject.ui.login.LoginActivity
import com.example.sideproject.ui.main.MainActivity
import com.example.sideproject.utils.SharePreferenceManager.getToken
import java.util.*

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        val isLogin = getToken().isNotEmpty()
        runSplash(isLogin)
    }

    private fun runSplash(isLogin : Boolean) {
        Timer().run {
            val launchTask = object : TimerTask() {
                override fun run() {
                    finish()
                    if (isLogin)
                        // TODO　refresh Token ?
                        startActivity(
                            Intent(this@LaunchActivity, MainActivity::class.java)
                        )
                    else
                        startActivity(
                            Intent(this@LaunchActivity, LoginActivity::class.java)
                        )
                }
            }
            schedule(launchTask, 1000)
        }
    }
}
