package com.jotangi.strollthroughbeimenisland.model

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("responseMessage")
    val responseMessage: String
)