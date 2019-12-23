package com.example.sideproject.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sideproject.utils.Constant.TABLE_USER
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_USER)
data class UserInfo (
    @SerializedName("email")
    @PrimaryKey
    @ColumnInfo
    var email : String,

    @SerializedName("name")
    @ColumnInfo
    var name : String,

    @SerializedName("gender")
    @ColumnInfo
    var gender : String,

    @SerializedName("isVIP")
    @ColumnInfo
    var isVIP : Boolean) : Parcelable