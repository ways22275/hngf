package com.example.sideproject.data.model

import com.google.gson.annotations.SerializedName

data class Account (
    @SerializedName("token")
    var token : String?,

    @SerializedName("email")
    var email : String,

    @SerializedName("name")
    var name : String,

    @SerializedName("gender")
    var gender : String,

    @SerializedName("isVIP")
    var isVIP : Boolean
)

fun toUserEntity(account: Account) : UserInfo {
    return UserInfo(
        name = account.name,
        email = account.email,
        gender = account.gender,
        isVIP = account.isVIP
    )
}