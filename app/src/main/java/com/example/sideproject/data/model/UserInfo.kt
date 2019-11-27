package com.example.sideproject.data.model

import com.google.gson.annotations.SerializedName

data class UserInfo (
    @SerializedName("name")
    var name : String?,
    @SerializedName("email")
    var email : String?,
    @SerializedName("gender")
    var gender : String?,
    @SerializedName("isVIP")
    var isVIP : Boolean
)