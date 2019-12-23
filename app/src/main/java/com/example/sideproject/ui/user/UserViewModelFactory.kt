package com.example.sideproject.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sideproject.App.Companion.roomDatabase
import com.example.sideproject.data.remote.ServiceClient
import com.example.sideproject.data.remote.login.LoginRepository
import com.example.sideproject.data.remote.user.UserRepository

class UserViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            val service = ServiceClient.getService()
            return UserViewModel(
                userRepository = UserRepository(
                    service = service,
                    userModel = roomDatabase.userModel()
                ),
                loginRepository = LoginRepository(
                    service = service,
                    userModel = roomDatabase.userModel()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}