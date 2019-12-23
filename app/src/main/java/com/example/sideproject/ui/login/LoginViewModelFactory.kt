package com.example.sideproject.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sideproject.App
import com.example.sideproject.data.remote.ServiceClient
import com.example.sideproject.data.remote.login.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    service = ServiceClient.getService(),
                    userModel = App.roomDatabase.userModel()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
