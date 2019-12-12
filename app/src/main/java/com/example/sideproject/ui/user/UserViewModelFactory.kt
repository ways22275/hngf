package com.example.sideproject.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
                    service = service
                ),
                loginRepository = LoginRepository(
                    service = service
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}