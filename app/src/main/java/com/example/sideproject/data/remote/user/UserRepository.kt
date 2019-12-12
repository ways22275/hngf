package com.example.sideproject.data.remote.user

import com.example.sideproject.data.model.ApiBaseResponse
import com.example.sideproject.data.model.UserInfo
import com.example.sideproject.data.remote.Service
import io.reactivex.Single

class UserRepository (private val service: Service) {

    fun getUserInfo() : Single<ApiBaseResponse<UserInfo>> = service.getProfile()

    fun putUserInfo(name : String) : Single<ApiBaseResponse<Int>> {
        val params = HashMap<String, String>()
        params["name"] = name
        return service.putProfile(params)
    }
}