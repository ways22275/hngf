package com.example.sideproject.data.model

import com.google.gson.annotations.SerializedName

data class Account (
    @SerializedName("token")
    var token : String?
)