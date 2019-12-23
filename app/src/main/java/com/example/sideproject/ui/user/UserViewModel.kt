package com.example.sideproject.ui.user

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sideproject.data.model.UserInfo
import com.example.sideproject.data.remote.ApiError
import com.example.sideproject.data.remote.login.LoginRepository
import com.example.sideproject.data.remote.user.UserRepository
import com.example.sideproject.ui.user.vo.UserEditResult
import com.example.sideproject.ui.user.vo.UserFormState
import com.example.sideproject.utils.RxTransFormers.applySchedulerSingle
import com.example.sideproject.utils.SharePreferenceManager.putToken
import io.reactivex.disposables.CompositeDisposable

class UserViewModel (
    private val userRepository: UserRepository, private val loginRepository: LoginRepository) : ViewModel(){

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> = _userInfo

    private val _userForm = MutableLiveData<UserFormState>()
    val userFormState: LiveData<UserFormState> = _userForm

    private val _userEditResult = MutableLiveData<UserEditResult>()
    val userEditResult : LiveData<UserEditResult> = _userEditResult

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    fun getUser() {
        val disposable = userRepository.getUserInfoFromLocal()
            .compose(applySchedulerSingle())
            .subscribe(
                {
                    userInfo -> _userInfo.value = userInfo
                },
                {
                    e -> e.printStackTrace()
                })
        compositeDisposable.add(disposable)
    }

    @SuppressLint("CheckResult")
    fun putUser(user : UserInfo) {
        val disposable = userRepository.putUserInfo(userInfo = user)
            .compose(applySchedulerSingle())
            .subscribe (
                {
                    response ->
                        if (response.status != 200) {
                            _userEditResult.value = UserEditResult(result = false, errorMsg = response.message)
                        } else {
                            _userEditResult.value = UserEditResult(result = true)
                        }
                },
                {
                    e ->
                        if (e != null) {
                            val errorMsg = ApiError(e).message
                            _userEditResult.value = UserEditResult(result = false, errorMsg = errorMsg)
                        }
                })
        compositeDisposable.add(disposable)
    }

    fun logout() {
        putToken("")
        loginRepository.logout()
    }

    fun userInfoChanged(name : String) {
        _userForm.value = UserFormState(isDataValid = isNameValid(name))
    }

    private fun isNameValid(name : String) : Boolean = name.isNotEmpty()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}