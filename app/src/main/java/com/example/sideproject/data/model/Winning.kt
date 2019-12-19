package com.example.sideproject.data.model

import com.google.gson.annotations.SerializedName

data class Winning(
    @SerializedName("numbers")
    var _winingList : ArrayList<Int>,
    @SerializedName("stage")
    var stage : String?,
    @SerializedName("createTime")
    var createTime : String?
)