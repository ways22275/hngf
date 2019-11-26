package com.example.sideproject.data.remote.login

import com.example.sideproject.data.Result
import com.example.sideproject.data.model.Account
import com.example.sideproject.data.model.ApiBaseResponse
import com.example.sideproject.data.model.LoggedInUser
import com.example.sideproject.data.remote.Service
import com.example.sideproject.utils.RxTransFormers.applySchedulerSingle
import com.example.sideproject.utils.SharePreferenceManager.putToken
import io.reactivex.Single

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(private val service: Service) {

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

    fun logout() {
        user = null
        service.logout()
    }

    fun register(email: String, password: String): Single<ApiBaseResponse<Account>> {
        val params = HashMap<String, String>()
        params["email"] = email
        params["password"] = password
        return service.register(params).doOnSuccess{
                response ->
            response.data?.token?.let { putToken(it) }
        }
    }

    fun login(email: String, password: String): Single<ApiBaseResponse<Account>> {
        val params = HashMap<String, String>()
        params["email"] = email
        params["password"] = password
        return service.login(params).doOnSuccess{
            response ->
            response.data?.token?.let { putToken(it) }
        }
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
