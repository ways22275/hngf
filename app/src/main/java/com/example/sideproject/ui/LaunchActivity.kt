package com.example.sideproject.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sideproject.R
import com.example.sideproject.data.remote.ServiceClient
import com.example.sideproject.data.remote.login.LoginRepository
import com.example.sideproject.ui.login.LoginActivity
import com.example.sideproject.ui.main.MainActivity
import com.example.sideproject.utils.RxTransFormers.applySchedulerSingle
import com.example.sideproject.utils.SharePreferenceManager.getToken
import com.example.sideproject.utils.SharePreferenceManager.putToken
import java.util.*

class LaunchActivity : AppCompatActivity() {

    private var isLogin : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        var token = getToken()
        isLogin = getToken().isNotEmpty()
        checkToken()
    }

    @SuppressLint("CheckResult")
    private fun checkToken() {
        if (isLogin) {
            val repository = LoginRepository(service = ServiceClient.getService())
            repository.refreshToken()
                .compose(applySchedulerSingle())
                .subscribe({
                    response ->
                        if (response.status != 200) {
                            autoLogout()
                        } else {
                            response.data?.token?.let { putToken(it) }
                            runSplash()
                        }
                },{ autoLogout() })
        } else {
            runSplash()
        }
    }

    private fun autoLogout() {
        putToken("")
        isLogin = false
        runSplash()
    }

    private fun runSplash() {
        Timer().run {
            val launchTask = object : TimerTask() {
                override fun run() {
                    finish()
                    if (isLogin)
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
