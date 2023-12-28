package com.jotangi.strollthroughbeimenisland.ui.home

import com.google.gson.annotations.SerializedName

data class BannerVO(

    @SerializedName("bid")
    val bid: String,

    @SerializedName("banner_name")
    val bannerName: String,

    @SerializedName("banner_startdate")
    val bannerStartdate: String,

    @SerializedName("banner_enddate")
    val bannerEnddate: String,

    @SerializedName("banner_description")
    val bannerDescription: String,

    @SerializedName("banner_picture")
    val bannerPicture: String,

    @SerializedName("banner_link")
    val bannerLink: String
)
