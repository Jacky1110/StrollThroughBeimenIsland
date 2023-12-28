package com.jotangi.strollthroughbeimenisland.ui.parking

import com.google.gson.annotations.SerializedName

data class ParkingVO(

    @SerializedName("parking_id")
    val parkingId: String,

    @SerializedName("parking_name")
    val parkingName: String,

    @SerializedName("parking_fee")
    val parkingFee: String,

    @SerializedName("parking_phone")
    val parkingPhone: String,

    @SerializedName("parking_address")
    val parkingAddress: String,

    @SerializedName("parking_latitude")
    val parkingLatitude: String,

    @SerializedName("parking_longitude")
    val parkingLongitude: String,
)
