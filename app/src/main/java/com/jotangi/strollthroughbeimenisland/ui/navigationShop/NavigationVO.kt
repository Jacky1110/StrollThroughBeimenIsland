package com.jotangi.strollthroughbeimenisland.ui.navigationShop

import com.google.gson.annotations.SerializedName

data class NavigationVO(


    @SerializedName("tourism_id")
    val tourismId: String,

    @SerializedName("tourism_name")
    val tourismName: String,

    @SerializedName("tourism_description")
    val tourismDescription: String,

    @SerializedName("tourism_open_time")
    val tourismOpenTime: String,

    @SerializedName("tourism_ticket_info")
    val tourismTicketInfo: String,

    @SerializedName("tourism_phone")
    val tourismPhone: String,

    @SerializedName("tourism_website")
    val tourismWebsite: String,

    @SerializedName("tourism_traffic")
    val tourismTraffic: String,

    @SerializedName("tourism_picture")
    val tourismPicture: String,

    @SerializedName("tourism_address")
    val tourismAddress: String,

    @SerializedName("tourism_latitude")
    val tourismlatitude: String,

    @SerializedName("tourism_longitude")
    val tourismLongitude: String,

)
