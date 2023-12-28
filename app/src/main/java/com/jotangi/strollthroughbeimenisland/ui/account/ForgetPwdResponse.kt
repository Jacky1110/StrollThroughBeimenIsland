package com.jotangi.strollthroughbeimenisland.ui.account

import com.google.gson.annotations.SerializedName

data class ForgetPwdResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("responseMessage")
    val responseMessage: String
)
