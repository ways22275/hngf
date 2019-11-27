package com.example.sideproject.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sideproject.data.model.UserInfo
import com.example.sideproject.data.remote.login.LoginRepository
import com.example.sideproject.data.remote.user.UserRepository
import com.example.sideproject.utils.RxTransFormers.applySchedulerSingle
import com.example.sideproject.utils.SharePreferenceManager.putToken
import io.reactivex.disposables.CompositeDisposable

class UserViewModel (
    private val userRepository: UserRepository, private val loginRepository: LoginRepository) : ViewModel(){

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> = _userInfo

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    fun getUser() {
        val disposable = userRepository.getUserInfo()
            .compose(applySchedulerSingle())
            .subscribe(
                {
                    response -> _userInfo.value = response.data
                },
                {
                    e -> e.printStackTrace()
                })
        compositeDisposable.add(disposable)
    }

    fun putUser() {

    }

    fun logout() {
        putToken("")
        loginRepository.logout()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}