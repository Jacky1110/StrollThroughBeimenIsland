package com.jotangi.strollthroughbeimenisland.ui

import com.google.gson.annotations.SerializedName

data class LogoutResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("responseMessage")
    val responseMessage: String
)
