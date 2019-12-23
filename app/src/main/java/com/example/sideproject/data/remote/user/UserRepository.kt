package com.example.sideproject.data.remote.user

import com.example.sideproject.data.local.User.UserDao
import com.example.sideproject.data.model.ApiBaseResponse
import com.example.sideproject.data.model.UserInfo
import com.example.sideproject.data.remote.Service
import io.reactivex.Single

class UserRepository (private val service: Service, private val userModel: UserDao) {

    fun getUserInfo() : Single<ApiBaseResponse<UserInfo>> = service.getProfile()

    fun getUserInfoFromLocal() : Single<UserInfo> = userModel.get()

    fun putUserInfo(userInfo: UserInfo) : Single<ApiBaseResponse<Int>> {
        val params = HashMap<String, String?>()
        params["name"] = userInfo.name
        return service.putProfile(params)
            .doOnSuccess { result ->
                if (result.status == 200) {
                    setUserInfoLocal(UserInfo(
                        name = userInfo.name, email = userInfo.email, gender = userInfo.gender, isVIP = userInfo.isVIP))
                }
            }
    }

    private fun setUserInfoLocal(userInfo: UserInfo) = userModel.merge(userInfo)
}