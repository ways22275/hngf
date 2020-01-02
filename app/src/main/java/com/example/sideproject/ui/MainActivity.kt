package com.example.sideproject.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sideproject.App.Companion.roomDatabase
import com.example.sideproject.R
import com.example.sideproject.data.remote.ServiceClient
import com.example.sideproject.data.remote.user.UserRepository
import com.example.sideproject.utils.RxTransFormers
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    // TODO Add BaseActivity
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_play,
                R.id.nav_winning_list,
                R.id.nav_chat,
                R.id.nav_profile
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        updateDrawerInfo(navView.getHeaderView(0))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    @SuppressLint("CheckResult")
    private fun updateDrawerInfo(headView : View) {
        val account = headView.findViewById<TextView>(R.id.textView_user_account)
        val repository = UserRepository(
            service = ServiceClient.getService(), userModel = roomDatabase.userModel())
        repository.getUserInfoFromLocal()
            .compose(RxTransFormers.applySchedulerSingle())
            .subscribe(
                {
                    if (it != null)
                        account.text = it.email
                },
                { e -> e.printStackTrace() })
    }
}
