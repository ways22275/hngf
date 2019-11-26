package com.example.sideproject.data.model

import com.google.gson.annotations.SerializedName

data class ApiBaseResponse<T> (

    @SerializedName("status")
    var status : Int,

    @SerializedName("message")
    var message : String?,

    @SerializedName("data")
    var data : T?
)