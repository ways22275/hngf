package com.example.sideproject.data.remote

import com.google.gson.JsonParser
import retrofit2.HttpException

class ApiError constructor(error : Throwable){
    var message = "An error occurred"

    init {
        message = if (error is HttpException) {
            val errorJsonString = error.response().errorBody()?.string()
            JsonParser().parse(errorJsonString)
                .asJsonObject["message"]
                .asString
        } else {
            error.message ?: message
        }
    }
}