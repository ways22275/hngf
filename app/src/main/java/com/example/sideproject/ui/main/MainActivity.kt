package com.example.sideproject.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sideproject.R
import com.example.sideproject.ui.login.LoginActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userName = findViewById<TextView>(R.id.user_name)
        val userEmail = findViewById<TextView>(R.id.user_email)
        val userGender = findViewById<TextView>(R.id.user_gender)
        val userIsVIP = findViewById<TextView>(R.id.user_isVIP)

        logout.setOnClickListener {
            val builder = AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.confirm_logout))
                setPositiveButton(getString(R.string.confirm)) { _, _ ->
                    userViewModel.logout()
                    startActivity(
                        Intent(this@MainActivity, LoginActivity::class.java)
                    )
                    finish()
                }
                setNegativeButton(getString(R.string.cancel)) { _, _ ->}
            }

            val dialog = builder.create()
            dialog.show()
        }

        userViewModel = ViewModelProviders.of(this@MainActivity, UserViewModelFactory())
            .get(UserViewModel::class.java)

        userViewModel.userInfo.observe(this@MainActivity, Observer {
            val userInfo = it ?: return@Observer
            userName.text = userInfo.name ?: getString(R.string.name_undefined)
            userEmail.text = userInfo.email
            userGender.text = userInfo.gender ?: getString(R.string.gender_undefined)
            userIsVIP.text = if (userInfo.isVIP) getString(R.string.isVip) else getString(R.string.isNotVip)
        })

        userViewModel.getUser()
    }
}
