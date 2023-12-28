package com.jotangi.strollthroughbeimenisland.ui.goodFood

import com.google.gson.annotations.SerializedName

data class GoodFoodListVO(

    @SerializedName("food_id")
    val foodId: String,

    @SerializedName("food_name")
    val foodName: String,

    @SerializedName("food_description")
    val foodDescription: String,

    @SerializedName("food_order_description")
    val foodOrderDescription: String,

    @SerializedName("food_storeid")
    val foodStoreid: String,

    @SerializedName("store_id")
    val storeId: String,

    @SerializedName("store_name")
    val storeName: String,

    @SerializedName("store_open_time")
    val storeOpenTime: String,

    @SerializedName("food_picture")
    val foodPicture: String,

    @SerializedName("food_phone")
    val foodPhone: String,

    @SerializedName("food_website")
    val foodWebsite: String,
)
