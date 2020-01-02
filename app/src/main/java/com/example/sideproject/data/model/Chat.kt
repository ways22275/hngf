package com.example.sideproject.data.model

data class Chat (
    var from : String?,
    var content : String?,
    var type : String?,
    var isConnect : Boolean = false
)

fun toChatConnectEntity(isConnect: Boolean) : Chat {
    return Chat(
        from = null,
        content = null,
        type = null,
        isConnect = isConnect
    )
}