package com.jotangi.strollthroughbeimenisland.ui.member

import com.google.gson.annotations.SerializedName

data class MemberInfoEditResponse(


    @SerializedName("status")
    val status: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("responseMessage")
    val responseMessage: String
)
