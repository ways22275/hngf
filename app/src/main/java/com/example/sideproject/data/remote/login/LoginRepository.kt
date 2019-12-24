package com.example.sideproject.data.remote.login

import com.example.sideproject.data.local.User.UserDao
import com.example.sideproject.data.model.*
import com.example.sideproject.data.remote.Service
import com.example.sideproject.utils.SharePreferenceManager.putToken
import io.reactivex.Completable
import io.reactivex.Single





/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(private val service: Service, private val userModel: UserDao) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() : Completable{
        putToken("")
        return Completable.fromAction {  userModel.logout() }
    }

    fun register(email: String, password: String): Single<ApiBaseResponse<Account>> {
        val params = HashMap<String, String>()
        params["email"] = email
        params["password"] = password
        return service.register(params)
            .flatMap {
                    response ->
                response.data?.token?.let { putToken(it) }
                service.getAccountProfile()
            }.doOnSuccess {
                    accountResponse ->
                val userInfo = accountResponse.data?.let { toEntity(it) }
                if (userInfo != null) {
                    setUserInfoLocal(userInfo)
                }
            }
    }

    fun login(email: String, password: String): Single<ApiBaseResponse<Account>> {
        val params = HashMap<String, String>()
        params["email"] = email
        params["password"] = password
        return service.login(params)
            .flatMap {
                    response ->
                        response.data?.token?.let { putToken(it) }
                        service.getAccountProfile()
            }.doOnSuccess {
                accountResponse ->
                    val userInfo = accountResponse.data?.let { toEntity(it) }
                    if (userInfo != null) {
                        setUserInfoLocal(userInfo)
                    }
            }
    }

    fun refreshToken() : Single<ApiBaseResponse<Account>>{
        return service.refreshToken()
            .flatMap {
                    response ->
                response.data?.token?.let { putToken(it) }
                service.getAccountProfile()
            }.doOnSuccess {
                    accountResponse ->
                val userInfo = accountResponse.data?.let { toEntity(it) }
                if (userInfo != null) {
                    setUserInfoLocal(userInfo)
                }
            }
    }

    private fun setUserInfoLocal(userInfo: UserInfo) = userModel.merge(userInfo)

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
