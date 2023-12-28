package com.jotangi.strollthroughbeimenisland.ui.storeIntroduction

import com.google.gson.annotations.SerializedName

data class StoreListVO(

    @SerializedName("store_id")
    val storeId: String,

    @SerializedName("store_name")
    val storeName: String,

    @SerializedName("store_phone")
    val storePhone: String,

    @SerializedName("store_website")
    val storeWebsite: String,

    @SerializedName("store_open")
    val storeOpen: String,

    @SerializedName("store_description")
    val storeDescription: String,

    @SerializedName("store_open_time")
    val storeOpenTime: String,

    @SerializedName("store_picture")
    val storePicture: String,

    @SerializedName("store_address")
    val storeAddress: String,

    @SerializedName("store_latitude")
    val storeLatitude: String,

    @SerializedName("store_longitude")
    val storeLongitude: String,

    )
