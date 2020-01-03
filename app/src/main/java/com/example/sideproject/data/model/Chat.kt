package com.example.sideproject.data.model

data class Chat (
    var from : String,
    var content : String,
    var type : Int,
    var isConnect : Boolean = false
)

fun toChatConnectEntity(isConnect: Boolean) : Chat {
    return Chat(
        from = "",
        content = "",
        type = -1,
        isConnect = isConnect
    )
}