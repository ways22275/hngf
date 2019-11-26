package com.example.sideproject.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.sideproject.data.remote.login.LoginRepository
import com.example.sideproject.data.Result

import com.example.sideproject.R
import com.example.sideproject.data.model.Account
import com.example.sideproject.data.model.ApiBaseResponse
import com.example.sideproject.utils.RxTransFormers.applySchedulerSingle
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val compositeDisposable = CompositeDisposable()

    fun login(email: String, password: String) {
        val disposable =
            loginRepository.login(email, password)
                .compose(applySchedulerSingle())
                .subscribe(
                    {
                        response ->
                        if (response.status != 200) {
                            _loginResult.value = LoginResult(error = R.string.login_failed, errorMsg = response.message)
                        } else {
                            _loginResult.value =
                                LoginResult(success = LoggedInUserView(displayName = email))
                        }
                    },
                    {
                        e -> _loginResult.value = LoginResult(error = R.string.login_failed, errorMsg = e.message)
                    }
                )

        compositeDisposable.add(disposable)
    }

    fun register(email: String, password: String) {
        val disposable =
            loginRepository.register(email, password)
                .compose(applySchedulerSingle())
                .subscribe(
                    {
                            response ->
                        if (response.status != 200) {
                            _loginResult.value = LoginResult(error = R.string.login_failed, errorMsg = response.message)
                        } else {
                            _loginResult.value =
                                LoginResult(success = LoggedInUserView(displayName = email))
                        }
                    },
                    {
                            e -> _loginResult.value = LoginResult(error = R.string.login_failed, errorMsg = e.message)
                    }
                )

        compositeDisposable.add(disposable)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
