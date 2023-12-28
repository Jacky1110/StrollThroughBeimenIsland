package com.jotangi.strollthroughbeimenisland.ui.beimenManager

import com.google.gson.annotations.SerializedName

data class WriteOffTicketResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("responseMessage")
    val responseMessage: String

)
